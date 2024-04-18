package com.Premate.payload;

import java.sql.Date;

import com.Premate.Model.Education;
import com.Premate.Model.Experience;
import com.Premate.Model.Name;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {
	private Name teacherName;
	private Date dobDate;
	private String mobNum;
	private String email;
	private String password;
	private int adharNum;
	private Experience experience;
	private Education education;
}
