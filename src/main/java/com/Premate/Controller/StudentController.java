package com.Premate.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Premate.Service.StudentService;
import com.Premate.payload.StudentDto;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	@PostMapping("/register")
	public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto){
		StudentDto stud = studentService.createStud(studentDto);
		return new ResponseEntity<StudentDto>(stud,HttpStatus.CREATED);
		
	}

}
