package com.Premate.Model;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Batch {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int batch_id;
	private String batchName;
	private LocalTime startTime;
    private LocalTime endTime;
    private Integer noOfStudents;
    @OneToMany(mappedBy = "batch")
    private List<Grade> grade;
    @OneToMany(mappedBy = "batch")
    private List<Student> students;
}
