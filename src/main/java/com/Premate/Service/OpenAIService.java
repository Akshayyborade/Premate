package com.Premate.Service;

import java.util.List;

public interface OpenAIService {
    List<String> generateQuestions(String prompt, int count,String type, String subject);
} 