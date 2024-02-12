package com.Premate.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subjects {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int subject_id;
	private String SubName;
	private int noOfChap;
	@ManyToOne
	@JoinColumn(name = "grade_id")
	private Grade grade;
	@ManyToOne
	private Exam exam;
	
	@ManyToOne
	private Timetable timetable;


}
