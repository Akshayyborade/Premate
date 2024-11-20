package com.Premate.dto;

import lombok.Data;
import java.util.List;
import lombok.Builder;
import java.util.ArrayList;
import java.util.UUID;

@Builder
@Data
public class Question {
    private String id;
    private String text;
    private String type;
    private String difficulty;
    private int marks;
    private List<String> options;
    private String answer;

    public static Question fromFormattedString(String formattedQuestion, String difficulty, String type) {
        String[] parts = formattedQuestion.split("\n");
        String questionText = "";
        List<String> optionsList = new ArrayList<>();
        String correctAnswer = null;

        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("Q:")) {
                questionText = part.substring(2).trim();
            } else if (part.matches("[A-D]\\).*")) {
                optionsList.add(part.trim());
            } else if (part.startsWith("Correct:")) {
                correctAnswer = part.substring(8).trim();
            }
        }

        return Question.builder()
                .id(UUID.randomUUID().toString())
                .text(questionText)
                .type(type)
                .difficulty(difficulty)
                .marks(calculateMarks(difficulty))
                .options(optionsList)
                .answer(correctAnswer)
                .build();
    }

    private static int calculateMarks(String difficulty) {
        return switch (difficulty.toUpperCase()) {
            case "EASY" -> 1;
            case "MEDIUM" -> 2;
            case "HARD" -> 3;
            default -> 1;
        };
    }
} 