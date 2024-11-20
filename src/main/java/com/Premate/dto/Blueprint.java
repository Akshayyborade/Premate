package com.Premate.dto;

import lombok.Data;
import java.util.Map;

@Data
public class Blueprint {
    private Integer totalQuestions;
    private Integer mandatoryQuestions;
    private Integer optionalQuestions;
    private Map<String, Integer> sectionWiseMarks;
} 