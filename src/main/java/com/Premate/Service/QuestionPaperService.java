package com.Premate.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.util.Assert;

import com.Premate.dto.Question;
import com.Premate.dto.QuestionPaper;
import com.Premate.Exception.QuestionGenerationException;
import com.Premate.Exception.DocumentGenerationException;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class QuestionPaperService {
    private static final Logger log = LoggerFactory.getLogger(QuestionPaperService.class);
    
    private final PaLMServiceImpl questionGenerator;
    private final DocumentGenerationService documentGenerationService;
    private final MeterRegistry meterRegistry;

    public QuestionPaperService(
            PaLMServiceImpl questionGenerator,
            DocumentGenerationService documentGenerationService,
            MeterRegistry meterRegistry) {
        this.questionGenerator = questionGenerator;
        this.documentGenerationService = documentGenerationService;
        this.meterRegistry = meterRegistry;
    }

    @Cacheable(value = "questions", key = "#type + #count + #difficulty + #subject + #classLevel + #language")
    @Retryable(value = {QuestionGenerationException.class}, maxAttempts = 3)
    public List<Question> generateQuestions(
            @NotBlank(message = "Question type cannot be empty") String type,
            @Min(value = 1, message = "Count must be at least 1") int count,
            @NotBlank(message = "Difficulty cannot be empty") String difficulty,
            @NotBlank(message = "Subject cannot be empty") String subject,
            @NotBlank(message = "Class level cannot be empty") String classLevel,
            @NotBlank(message = "Language cannot be empty") String language) {
        
        Timer.Sample timer = Timer.start(meterRegistry);
        
        try {
            log.info("Generating {} {} questions for {} class {}", count, type, subject, classLevel);
            
            validateInputs(type, difficulty, subject);
            String prompt = buildPrompt(type, difficulty, subject, classLevel, language);
            List<String> generatedQuestions = questionGenerator.generateQuestions(prompt, count, type, subject,language, classLevel);
            List<Question> questions = convertToQuestions(generatedQuestions, type, difficulty);
            
            timer.stop(meterRegistry.timer("question.generation.time", 
                "type", type, 
                "subject", subject));
            
            log.info("Successfully generated {} questions", questions.size());
            return questions;
            
        } catch (Exception e) {
            log.error("Failed to generate questions", e);
            throw new QuestionGenerationException("Failed to generate questions", e);
        }
    }

    public byte[] generateDocument(@NotNull(message = "Paper cannot be null") QuestionPaper paper,
                                 @NotBlank(message = "Format cannot be empty") String format) {
        Timer.Sample timer = Timer.start(meterRegistry);
        
        try {
            log.info("Generating {} document for paper ID: {}", 
                    format, paper.getMetadata().getPaperId());
            
            byte[] document = switch (format.toUpperCase()) {
                case "PDF" -> documentGenerationService.generatePDF(paper);
                case "DOCX" -> documentGenerationService.generateDOCX(paper);
                default -> throw new IllegalArgumentException("Unsupported format: " + format);
            };
            
            timer.stop(meterRegistry.timer("document.generation.time", 
                "format", format));
            
            log.info("Successfully generated {} document", format);
            return document;
            
        } catch (Exception e) {
            log.error("Failed to generate document in {} format", format, e);
            throw new DocumentGenerationException("Failed to generate document", e);
        }
    }

    private void validateInputs(String type, String difficulty, String subject) {
        Assert.isTrue(List.of("MCQ", "DESCRIPTIVE").contains(type.toUpperCase()),
                "Invalid question type: " + type);
        Assert.isTrue(List.of("EASY", "MEDIUM", "HARD").contains(difficulty.toUpperCase()),
                "Invalid difficulty level: " + difficulty);
        Assert.hasText(subject, "Subject cannot be empty");
    }

    private String buildPrompt(String type, String difficulty, String subject, 
                             String classLevel, String language) {
        StringBuilder promptBuilder = new StringBuilder()
            .append("Generate detailed educational ")
            .append(type)
            .append(" questions with the following specifications:\n")
            .append("- Subject: ").append(subject).append("\n")
            .append("- Class Level: ").append(classLevel).append("\n")
            .append("- Difficulty: ").append(difficulty).append("\n")
            .append("- Language: ").append(language).append("\n\n")
            .append("Requirements:\n")
            .append("1. Clear, concise question text\n")
            .append("2. Age-appropriate content\n")
            .append("3. Curriculum-aligned material\n");

        if (type.equalsIgnoreCase("MCQ")) {
            promptBuilder.append("4. Four distinct options (A, B, C, D)\n")
                        .append("5. One correct answer marked\n")
                        .append("6. No ambiguous options\n");
        }

        return promptBuilder.toString();
    }

    private List<Question> convertToQuestions(List<String> generatedQuestions, 
                                            String type, 
                                            String difficulty) {
        List<Question> questions = new ArrayList<>();
        for (String questionText : generatedQuestions) {
            Question question = Question.fromFormattedString(questionText, difficulty, type);
            questions.add(question);
        }
        return questions;
    }
} 