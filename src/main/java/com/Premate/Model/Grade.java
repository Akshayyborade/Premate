package com.Premate.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Grade {
	
	private int grade_id;
	private String gradeName;
	private Subjects subjects;
	private Student students;
	

}
