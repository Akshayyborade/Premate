package com.Premate.Model;

import java.sql.Date;
import java.time.DayOfWeek;

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

public class Timetable {

	private int timetable_id;
	private DayOfWeek dayOfWeek;
	private Date date;
	private Subjects subjects;

}
