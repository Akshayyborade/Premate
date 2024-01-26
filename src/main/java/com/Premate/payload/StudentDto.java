package com.Premate.payload;

import java.sql.Date;

import com.Premate.Model.Grade;
import com.Premate.Model.Name;
import com.Premate.Model.Parents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
	private int stud_id;
	private Name studentName;
	private String schoolName;
	private String mobNumber;
	private String email;
	private String password;
	private String gender;
	private Date dobDate;
	private Parents parents;
	private Grade grade;
}
