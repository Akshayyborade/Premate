package com.Premate.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.net.SocketException;
import java.io.IOException;

@Service
@Slf4j
public class PaLMServiceImpl implements OpenAIService {

    @Value("${palm.api.key}")
    private String apiKey;

    private static final String PALM_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=";

    @Retryable(
        value = { SocketException.class, IOException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000) // 1 second delay between retries
    )
    @Override
    public List<String> generateQuestions(String prompt, int count, String type, String subject, String language, String classLevel, String difficulty) throws SocketException , IOException {
        try {
            List<String> allQuestions = new ArrayList<>();
            int questionsPerBatch = 20;
            
            while (allQuestions.size() < count) {
                int remainingQuestions = Math.min(questionsPerBatch, count - allQuestions.size());
                List<String> questions = generateQuestionsFromPaLM(remainingQuestions, type, subject, language, classLevel,  difficulty);
                allQuestions.addAll(questions);
                Thread.sleep(1000);
                log.info("Progress: Generated {}/{} questions", allQuestions.size(), count);
            }
            
            return allQuestions;
        } catch (SocketException e) {
            log.error("Network connection error while generating questions. Will retry.", e);
            throw e;
        } catch (IOException e) {
            log.error("I/O error while generating questions. Will retry.", e);
            throw e;
        } catch (Exception e) {
            log.error("Failed to get questions from PaLM API: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private List<String> generateQuestionsFromPaLM(int count, String type, String subject, String language, String classLevel, String difficulty ) throws SocketException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(
            Map.of(
                "parts", List.of(
                    Map.of("text", createPrompt(count, type, subject, language, classLevel, difficulty))
                )
            )
        ));

        Map<String, Object> generationConfig = new HashMap<>();
        generationConfig.put("temperature", 0.7);
        generationConfig.put("topK", 40);
        generationConfig.put("topP", 0.95);
        generationConfig.put("maxOutputTokens", 2048);
        requestBody.put("generationConfig", generationConfig);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        String fullUrl = PALM_API_URL + apiKey;

        try {
            log.debug("Sending request to Gemini API");
            ResponseEntity<String> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.POST,
                request,
                String.class
            );
            
            log.debug("Received response from Gemini API: {}", response.getBody());
            return parseGeminiResponse(response.getBody());
        } catch (Exception e) {
            log.error("Error calling Gemini API: {}", e.getMessage(), e);
            throw e;
        }
    }

   
        private String createPrompt(int count, String type, String subject, String language, String classLevel, String difficulty) {
            String questionFormat = switch (type.toLowerCase()) {
                case "mcq" -> """
                    Q: [Question text]
                    A) [Option A]
                    B) [Option B]
                    C) [Option C]
                    D) [Option D]
                    Correct: [Letter of correct answer]""";
                case "short" -> """
                    Q: [Question text]
                    Answer: [Concise answer in 30-50 words]
                    Marks: [2-3 marks]""";
                case "descriptive" -> """
                    Q: [Question text]
                    Answer: [Detailed explanation with key points]
                    Marks: [3-5 marks]""";
                default -> throw new IllegalArgumentException("Invalid question type: " + type);
            };
    
            return String.format("""
                You are an expert teacher. Generate exactly %d %s questions for %s subject in %s language.
                The questions should be suitable for %s grade students and at a %s difficulty level.
                
                Important Instructions:
                1. Generate exactly %d questions, no more, no less
                2. Each question MUST follow this EXACT format:
                   %s
                3. Use clear and precise language in %s
                4. Make questions challenging but appropriate for %s grade
                5. Include a mix of theoretical and application-based questions
                6. Ensure questions align with %s curriculum
                
                Focus on current curriculum topics in %s.
                Do not include any additional text, explanations, or formatting.
                Start each question with 'Q:' and nothing else.
                """, count, type, subject, language, classLevel, difficulty, 
                     count, questionFormat, language, classLevel, classLevel, subject);
     }

    

    private List<String> parseGeminiResponse(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            
            // Navigate through the response structure
            JsonNode candidates = root.path("candidates");
            if (candidates.isEmpty()) {
                log.error("No candidates in response");
                return Collections.emptyList();
            }

            JsonNode content = candidates.get(0).path("content");
            if (content.isEmpty()) {
                log.error("No content in first candidate");
                return Collections.emptyList();
            }

            JsonNode parts = content.path("parts");
            if (parts.isEmpty()) {
                log.error("No parts in content");
                return Collections.emptyList();
            }

            String generatedText = parts.get(0).path("text").asText();
            log.debug("Generated text: {}", generatedText);

            List<String> questions = extractQuestions(generatedText, "mcq");
            log.info("Extracted {} questions from response", questions.size());
            
            return questions;
        } catch (Exception e) {
            log.error("Error parsing Gemini response: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private List<String> extractQuestions(String text, String type) {
        List<String> questions = new ArrayList<>();
        String[] possibleQuestions = text.split("(?=Q:)");

        for (String question : possibleQuestions) {
            question = question.trim();
            if (!question.isEmpty()) {
                questions.add(question);
                log.debug("Added valid question: {}", question);
            } else if (!question.isEmpty()) {
                log.debug("Skipped invalid question format: {}", question);
            }
        }

        return questions;
    }
    private boolean isValidQuestionFormat(String text, String type) {
        String trimmedText = text.trim(); // Trim whitespace
        boolean isValid = switch (type.toLowerCase()) {
            case "mcq" -> trimmedText.contains("Q:") && 
                         trimmedText.contains("A)") && 
                         trimmedText.contains("B)") && 
                         trimmedText.contains("C)") && 
                         trimmedText.contains("D)") && 
                         trimmedText.contains("Correct:");
            case "short", "descriptive" -> trimmedText.contains("Q:") && 
                                         trimmedText.contains("Answer:") && 
                                         trimmedText.contains("Marks:");
            default -> false;
        };
    
        if (!isValid) {
            log.debug("Skipped invalid question format: {}", trimmedText);
        } else {
            log.debug("Valid question format: {}", trimmedText);
        }
        return isValid;
    }

    // @Override
    // public List<String> generateQuestions(String prompt, int count, String type, String subject) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'generateQuestions'");
    // }
} 