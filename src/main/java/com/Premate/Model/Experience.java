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
public class Experience {
	private int exp_id;
	private String companyName;
	private Year joiningYear;

}
