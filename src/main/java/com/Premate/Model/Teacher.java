package com.Premate.Model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Teacher {
	@Id
	private int teacher_id;
	@OneToOne
	private Name teacherName;
	private Date dobDate;
	private String mobNum;
	private String email;
	private String password;
	private int adharNum;
//	private Experience experience;
//	private Education education;
	@Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
	

}
