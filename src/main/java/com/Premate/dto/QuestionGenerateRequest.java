package com.Premate.dto;

import lombok.Data;

@Data
public class QuestionGenerateRequest {
    private String type;
    private int count;
    private String difficulty;
    private String subject;
    private String classLevel;
    private String language;
} 