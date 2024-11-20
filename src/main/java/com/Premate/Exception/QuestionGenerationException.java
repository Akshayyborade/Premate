package com.Premate.Exception;

public class QuestionGenerationException extends RuntimeException {
    public QuestionGenerationException(String message) {
        super(message);
    }

    public QuestionGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
} 