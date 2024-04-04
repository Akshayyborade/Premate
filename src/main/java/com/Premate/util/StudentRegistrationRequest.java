package com.Premate.util;

import com.Premate.payload.StudentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegistrationRequest {
    private int adminId;
    private StudentDto studentDto;

    // Getters and setters
}
