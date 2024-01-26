package com.Premate.Model;

import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Education {
	private int edu_id;
	private String highestEducation;
	private Year yearOfpassing;

}
