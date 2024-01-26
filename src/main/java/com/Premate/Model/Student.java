package com.Premate.Model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int stud_id;
	@OneToOne
	private Name studentName;
	private String schoolName;
	private String mobNumber;
	private String email;
	private String password;
	private String gender;
	private Date dobDate;
//	private Parents parents;
//	private Grade grade;
	@Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

}
