package com.Premate.dto;

import lombok.Data;
import java.util.List;

@Data
public class Section {
    private String name;
    private int marks;
    private List<Question> questions;
} 