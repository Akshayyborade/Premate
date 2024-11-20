package com.Premate.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionPaper {
    private PaperMetadata metadata;
    private List<Section> sections;
}

 