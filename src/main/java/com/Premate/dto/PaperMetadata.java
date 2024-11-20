package com.Premate.dto;

import lombok.Data;

@Data
public class PaperMetadata {
    private String paperId;
    private String board;
    private String state;
    private String subject;
    private String classLevel;
    private Integer totalMarks;
    private Integer duration;
    private DifficultyDistribution difficultyDistribution;
    private QuestionTypeDistribution questionTypes;
    private String examType;
    private String language;
    private String paperFormat;
    private Boolean includeAnswerKey;
    private Blueprint blueprint;
} 