package com.Premate.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	@Id
	private int address_id;
	private String  street;
    private String  city;
    private String  state;
    private String  zip;
    @ManyToOne()
    @JoinColumn(name = "stud_id")
    private Student student;
}
