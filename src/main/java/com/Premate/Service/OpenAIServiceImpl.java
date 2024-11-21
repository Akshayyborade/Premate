//package com.Premate.Service;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpServerErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class OpenAIServiceImpl implements OpenAIService {
//
//    @Value("${huggingface.api.key}")
//    private String apiKey;
//
//    private static final String HUGGINGFACE_API_URL =
//        "https://api-inference.huggingface.co/models/google/flan-t5-base";
//
//    public static final List<String> MATH_QUESTIONS = Arrays.asList(
//        "Q: Solve the quadratic equation: x² + 5x + 6 = 0\n" +
//        "A) x = -2, -3\n" +
//        "B) x = 2, 3\n" +
//        "C) x = -1, -6\n" +
//        "D) x = 1, 6\n" +
//        "Correct: A",
//
//        "Q: What is the value of sin²θ + cos²θ?\n" +
//        "A) 1\n" +
//        "B) 0\n" +
//        "C) 2\n" +
//        "D) -1\n" +
//        "Correct: A",
//
//        "Q: If a triangle has angles measuring 70° and 40°, what is the measure of the third angle?\n" +
//        "A) 70°\n" +
//        "B) 80°\n" +
//        "C) 60°\n" +
//        "D) 50°\n" +
//        "Correct: A",
//
//        "Q: What is the slope of a line parallel to y = 3x + 4?\n" +
//        "A) 3\n" +
//        "B) 4\n" +
//        "C) -3\n" +
//        "D) 0\n" +
//        "Correct: A",
//
//        "Q: Simplify: √144\n" +
//        "A) 12\n" +
//        "B) 14\n" +
//        "C) 10\n" +
//        "D) 16\n" +
//        "Correct: A",
//
//        "Q: What is the area of a rectangle with length 8 units and width 6 units?\n" +
//        "A) 48 square units\n" +
//        "B) 28 square units\n" +
//        "C) 14 square units\n" +
//        "D) 96 square units\n" +
//        "Correct: A",
//
//        "Q: What is the value of π (pi) rounded to two decimal places?\n" +
//        "A) 3.14\n" +
//        "B) 3.41\n" +
//        "C) 3.12\n" +
//        "D) 3.24\n" +
//        "Correct: A",
//
//        "Q: If 3x + 7 = 22, what is the value of x?\n" +
//        "A) 5\n" +
//        "B) 6\n" +
//        "C) 7\n" +
//        "D) 4\n" +
//        "Correct: A",
//
//        "Q: What is the perimeter of a square with side length 9 units?\n" +
//        "A) 36 units\n" +
//        "B) 81 units\n" +
//        "C) 18 units\n" +
//        "D) 27 units\n" +
//        "Correct: A",
//
//        "Q: Factor: x² - 4\n" +
//        "A) (x+2)(x-2)\n" +
//        "B) (x+4)(x-1)\n" +
//        "C) (x+1)(x-4)\n" +
//        "D) (x+3)(x-1)\n" +
//        "Correct: A"
//    );
//
//    @Override
//    public List<String> generateQuestions(String prompt, int count, String type, String subject) {
//        try {
//            // First try to get questions from API
//            List<String> apiQuestions = tryGetQuestionsFromAPI(prompt, count);
//            if (!apiQuestions.isEmpty()) {
//                return apiQuestions;
//            }
//        } catch (Exception e) {
//            // Log the error but continue to backup questions
//            System.out.println("Failed to get questions from API: " + e.getMessage());
//        }
//
//        // If API fails or returns empty, use backup questions
//        return getBackupQuestions(count, subject);
//    }
//
//    private List<String> tryGetQuestionsFromAPI(String prompt, int count) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(apiKey);
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("inputs", prompt);
//        requestBody.put("parameters", Map.of(
//            "max_length", 2000,
//            "temperature", 0.8,
//            "top_p", 0.9,
//            "num_return_sequences", count
//        ));
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//            HUGGINGFACE_API_URL,
//            HttpMethod.POST,
//            request,
//            String.class
//        );
//
//        if (response.getBody() == null || response.getBody().isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        return parseGeneratedQuestions(response.getBody(), count);
//    }
//
//    private List<String> getBackupQuestions(int count, String subject) {
//        if ("Mathematics".equalsIgnoreCase(subject)) {
//            List<String> shuffled = new ArrayList<>(MATH_QUESTIONS);
//            Collections.shuffle(shuffled);
//            
//            // Randomize the correct answers and options for each question
//            return shuffled.subList(0, Math.min(count, shuffled.size()))
//                .stream()
//                .map(this::randomizeOptions)
//                .collect(Collectors.toList());
//        }
//        return Collections.emptyList();
//    }
//
//    private String randomizeOptions(String question) {
//        String[] parts = question.split("\n");
//        String questionText = parts[0];
//        List<String> options = new ArrayList<>();
//        
//        // Get options without their labels (A, B, C, D)
//        for (int i = 1; i <= 4; i++) {
//            options.add(parts[i].substring(3));
//        }
//        
//        // Get the correct answer's content
//        String correctAnswer = options.get(0); // Since original correct answer was always A
//        
//        // Shuffle options
//        Collections.shuffle(options);
//        
//        // Find new position of correct answer
//        String newCorrectLabel = "ABCD".charAt(options.indexOf(correctAnswer)) + "";
//        
//        // Rebuild question string
//        StringBuilder newQuestion = new StringBuilder(questionText + "\n");
//        for (int i = 0; i < options.size(); i++) {
//            newQuestion.append((char)('A' + i)).append(") ").append(options.get(i)).append("\n");
//        }
//        newQuestion.append("Correct: ").append(newCorrectLabel);
//        
//        return newQuestion.toString();
//    }
//
//    private List<String> parseGeneratedQuestions(String jsonResponse, int count) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode root = objectMapper.readTree(jsonResponse);
//            
//            List<String> questions = new ArrayList<>();
//            if (root.isArray()) {
//                for (JsonNode node : root) {
//                    String text = node.get("generated_text").asText();
//                    if (isValidQuestionFormat(text)) {
//                        questions.add(text);
//                    }
//                }
//            }
//            
//            if (questions.isEmpty()) {
//                return Collections.emptyList();
//            }
//            
//            return questions.subList(0, Math.min(count, questions.size()));
//        } catch (Exception e) {
//            return Collections.emptyList();
//        }
//    }
//
//    private boolean isValidQuestionFormat(String text) {
//        // Check if the text contains required MCQ elements
//        return text.contains("Q:") && 
//               text.contains("A)") && 
//               text.contains("B)") && 
//               text.contains("C)") && 
//               text.contains("D)") && 
//               text.contains("Correct:");
//    }
//
//    @Override
//    public List<String> generateQuestions(String prompt, int count, String type, String subject, String language,
//            String classLevel) {
//        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'generateQuestions'");
//    }
//}
