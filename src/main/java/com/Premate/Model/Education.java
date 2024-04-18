package com.Premate.Model;

import java.time.Year;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Education {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int edu_id;
	private String highestEducation;
	private Year yearOfpassing;

}
