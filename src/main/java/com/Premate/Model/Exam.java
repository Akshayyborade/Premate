package com.Premate.Model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Exam {

	private int exam_id;
	private String examName;
	private Date examDate;
	private String totalMark;
	private Subjects subjects;
	private String chapterNo;

}
