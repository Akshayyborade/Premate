package com.Premate.Service;

import java.util.List;
import java.io.IOException;
import java.net.SocketException;

public interface OpenAIService {
    List<String> generateQuestions(String prompt, int count, String type, String subject, String language, String classLevel) throws SocketException , IOException;
} 