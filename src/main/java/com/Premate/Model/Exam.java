package com.Premate.Model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
public class Exam {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int exam_id;
	private String examName;
	private Date examDate;
	private String totalMark;
	private String chapterNo;
	@ManyToMany
    private List<Subjects> subjects;

    @ManyToMany
    private List<Grade> grades;

    @ManyToMany 
    
    private List<Student> students;
    @OneToOne
    @JoinColumn(name="result_id")
    private Result result;
	

}
