package com.Premate.Service;

import java.util.List;

import com.Premate.payload.StudentDto;

public interface StudentService {
	//create
	StudentDto createStud(StudentDto studentDto);
	//update
	StudentDto updateStud(StudentDto studentDto, int id);
	//delete
	void deleteStud(int id);
	//get
	StudentDto getStud(int id);
	//getAll
	List<StudentDto> getAllStud();
	//get by email
	StudentDto getStudByEmail(String email);

}
