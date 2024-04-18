package com.Premate.Model;

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
public class Parents {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int parent_id;
	private String parentName;
	private String mobNo;
	private String email;
	private String location;
	private String relationWithStud;
	
}
