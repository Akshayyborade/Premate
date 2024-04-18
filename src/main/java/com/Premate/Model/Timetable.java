package com.Premate.Model;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Timetable {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int timetable_id;
	private DayOfWeek dayOfWeek;
	private Date date;
	@OneToMany
	@JoinColumn(name = "timetable_id")
	private List<Subjects> subjects;
	
	@OneToOne()
	@JoinColumn(name = "grade_id")
	private Grade grade;
	@OneToOne
	@JoinColumn(name = "student_id")
	private Student student;

}
