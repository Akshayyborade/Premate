package com.Premate.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.Premate.dto.PaperDownloadRequest;
import com.Premate.dto.QuestionGenerateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionPaperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void generateQuestions_WithValidRequest_ShouldReturnOk() throws Exception {
        QuestionGenerateRequest request = new QuestionGenerateRequest();
        // Set required fields in request
        
        mockMvc.perform(post("/api/generate-questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void generateQuestions_WithNullRequest_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/generate-questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void downloadPaper_WithValidRequest_ShouldReturnOk() throws Exception {
        PaperDownloadRequest request = new PaperDownloadRequest();
        // Set required fields in request

        mockMvc.perform(post("/api/download-paper")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void downloadPaper_WithNullRequest_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/download-paper")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }
} 