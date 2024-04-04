package com.Premate.Model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a student.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	/**
	 * Unique identifier for the student.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int stud_id;

	/**
	 * Reference to the name of the student.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Name studentName;

	/**
	 * The name of the school the student is attending.
	 */
	private String schoolName;

	/**
	 * Mobile number of the student.
	 */
	private String mobNumber;

	/**
	 * Email address of the student.
	 */
	private String email;

	/**
	 * Password for the student.
	 */
	private String password;

	/**
	 * Gender of the student.
	 */
	private String gender;

	/**
	 * Date of birth of the student.
	 */
	private Date dobDate;

	/**
	 * Reference to the parent(s) of the student.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	private Parents parents;

	/**
	 * Reference to the grade of the student.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gradeId")
	private Grade grade;

	/**
	 * Reference to the attendance of the student.
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
	private List<Attendance> attendance;

	private int adminId;

	@ManyToOne
	@JoinColumn(name = "batch_id")
	private Batch batch;

	@ManyToMany(mappedBy = "students")
	private List<Exam> exam;
	@ManyToOne(cascade = CascadeType.ALL)
	private Timetable timetable;
	@OneToMany(mappedBy = "student")
	private List<Result> results; 
	@ManyToOne(cascade = CascadeType.ALL)
	private Address address;

	/**
	 * Role of the student in the application.
	 */
	@Enumerated(EnumType.STRING)
	private AppUserRole appUserRole;

	// adding more field 14/03/24

	private boolean isactive = false;

	private LocalDate dateOfAddmission;

}
