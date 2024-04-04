package com.Premate.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
@Entity
public class Grade {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int gradeId;
	private String gradeName;
	private String stream;
	private String board;
	@ManyToOne(cascade = CascadeType.ALL)
	private Batch batch;
	@OneToMany(mappedBy = "grade")
   
	private List<Subjects> subjects;
	@OneToMany(mappedBy = "grade", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<StudentEnrollment> students;

	@ManyToOne
	@JoinColumn(name = "admin_id")
	private Admin admin;

	@OneToOne(mappedBy = "grade")
	private Timetable timetable;
	
}
