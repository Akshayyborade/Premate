package com.Premate.Model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Attendance {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int attendanceId;
	private Date date;
	private boolean isPresent;
	
	@ManyToOne
	@JoinColumn(name = "stud_id")
	
	private Student student;

	@ManyToOne
	@JoinColumn(name = "teacher_id")
	
	private Teacher teacher;

}
