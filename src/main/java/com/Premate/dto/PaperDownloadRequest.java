package com.Premate.dto;

import lombok.Data;

@Data
public class PaperDownloadRequest {
    private QuestionPaper paper;
    private String format;
} 