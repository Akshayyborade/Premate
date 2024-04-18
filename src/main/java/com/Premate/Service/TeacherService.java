package com.Premate.Service;

import java.util.List;

import com.Premate.payload.TeacherDto;

public interface TeacherService {
	//create
	TeacherDto createTeacher(TeacherDto teacherDto);
	
	//update
	TeacherDto updateTeacher(TeacherDto teacherDto, int id);
	//get
	TeacherDto getTeacher(int id);
	//getAll
	List<TeacherDto> getTeacherDtos();
	//delete
	void deleteTeacher(int id);
	//byEmail
	TeacherDto findByEmail(String email);

}
