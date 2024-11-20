package com.Premate.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaLMServiceImpl implements OpenAIService {

    @Value("${palm.api.key}")
    private String apiKey;

    private static final String PALM_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=";

    @Override
    public List<String> generateQuestions(String prompt, int count, String type, String subject) {
        try {
            List<String> allQuestions = new ArrayList<>();
            int questionsPerBatch = 20; // Gemini has limitations per request
            
            while (allQuestions.size() < count) {
                int remainingQuestions = Math.min(questionsPerBatch, count - allQuestions.size());
                List<String> questions = generateQuestionsFromPaLM(remainingQuestions, type, subject);
                allQuestions.addAll(questions);
                
                // Add a small delay between requests to avoid rate limiting
                Thread.sleep(1000);
                
                log.info("Progress: Generated {}/{} questions", allQuestions.size(), count);
            }
            
            log.info("Completed: Generated total {} questions", allQuestions.size());
            return allQuestions;
        } catch (Exception e) {
            log.error("Failed to get questions from PaLM API: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private List<String> generateQuestionsFromPaLM(int count, String type, String subject) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(
            Map.of(
                "parts", List.of(
                    Map.of("text", createPrompt(count, type, subject))
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

    private String createPrompt(int count, String type, String subject) {
        return String.format("""
            You are an expert teacher. Generate exactly %d multiple choice questions for %s subject.
            The questions should be suitable for 10th grade students.
            
            Important Instructions:
            1. Generate exactly %d questions, no more, no less
            2. Each question MUST follow this EXACT format:
               Q: [Question text]
               A) [Option A]
               B) [Option B]
               C) [Option C]
               D) [Option D]
               Correct: [Letter of correct answer]
            3. Make sure each question has exactly 4 options
            4. Include only ONE correct answer per question
            5. Make all options plausible
            6. Use clear and precise language
            7. Include a mix of conceptual and calculation questions
            8. Ensure questions are challenging but appropriate for 10th grade
            
            Focus on current curriculum topics in %s.
            Do not include any additional text, explanations, or formatting.
            Start each question with 'Q:' and nothing else.
            """, count, subject, count, subject);
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

            List<String> questions = extractQuestions(generatedText);
            log.info("Extracted {} questions from response", questions.size());
            
            return questions;
        } catch (Exception e) {
            log.error("Error parsing Gemini response: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private List<String> extractQuestions(String text) {
        List<String> questions = new ArrayList<>();
        String[] possibleQuestions = text.split("(?=Q:)");

        for (String question : possibleQuestions) {
            question = question.trim();
            if (question.startsWith("Q:") && isValidQuestionFormat(question)) {
                questions.add(question);
                log.debug("Added valid question: {}", question);
            } else if (!question.isEmpty()) {
                log.debug("Skipped invalid question format: {}", question);
            }
        }

        return questions;
    }

    private boolean isValidQuestionFormat(String text) {
        boolean isValid = text.contains("Q:") && 
                         text.contains("A)") && 
                         text.contains("B)") && 
                         text.contains("C)") && 
                         text.contains("D)") && 
                         text.contains("Correct:");
                         
        if (!isValid) {
            log.debug("Invalid question format: {}", text);
        }
        return isValid;
    }
} 