package com.Premate.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Premate.Service.QuestionPaperService;
import com.Premate.dto.PaperDownloadRequest;
import com.Premate.dto.Question;
import com.Premate.dto.QuestionGenerateRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/api")
public class QuestionPaperController {
    private final QuestionPaperService service;

    @Autowired
    public QuestionPaperController(QuestionPaperService service) {
        this.service = service;
    }

    @PostMapping("/generate-questions")
    public ResponseEntity<?> generateQuestions(@Valid @NotNull @RequestBody QuestionGenerateRequest request) {
        try {
            List<Question> questions = service.generateQuestions(
                request.getType(),
                request.getCount(), 
                request.getDifficulty(),
                request.getSubject(),
                request.getClassLevel(),
                request.getLanguage()
            );
            return ResponseEntity.ok(Map.of("questions", questions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/download-paper")
    public ResponseEntity<?> downloadPaper(@Valid @NotNull @RequestBody PaperDownloadRequest request) {
        try {
            byte[] document = service.generateDocument(
                request.getPaper(),
                request.getFormat()
            );
            
            String filename = "question_paper_" + request.getPaper().getMetadata().getPaperId() + 
                            "." + request.getFormat().toLowerCase();
            
            return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + filename)
                .body(document);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 