package com.Premate.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Premate.Exception.ResourceNotFoundException;
import com.Premate.Model.Name;
import com.Premate.Model.Student;
import com.Premate.Repository.NameRepo;
import com.Premate.Repository.StudentRepo;
import com.Premate.payload.StudentDto;
@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepo studentRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private NameRepo nameRepo;

	@Override
	public StudentDto createStud(StudentDto studentDto) {
		// TODO Auto-generated method stub
		Name name = studentDto.getStudentName();
		nameRepo.save(name);
		Student save = studentRepo.save(modelMapper.map(studentDto, Student.class));
		return modelMapper.map(save, StudentDto.class);
	}

	@Override
	public StudentDto updateStud(StudentDto studentDto, int id) {
		// TODO Auto-generated method stub
		Student student = studentRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Student", "StudentId", id));
		studentDto.setStud_id(student.getStud_id());
		modelMapper.map(studentDto, student);
		Student save = studentRepo.save(student);
		return modelMapper.map(save, StudentDto.class);
	}

	@Override
	public void deleteStud(int id) {
		// TODO Auto-generated method stub
		studentRepo.deleteById(id);

	}

	@Override
	public StudentDto getStud(int id) {
		// TODO Auto-generated method stub
		Student student = studentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student", "StudentId", id));
		return modelMapper.map(student, StudentDto.class);
	}

	@Override
	public List<StudentDto> getAllStud() {
		// TODO Auto-generated method stub
		List<StudentDto> studentDtos = studentRepo.findAll().stream().map(student->modelMapper.map(student, StudentDto.class)).collect(Collectors.toList());
		return studentDtos;
	}

	@Override
	public StudentDto getStudByEmail(String email) {
		// TODO Auto-generated method stub
		Student student = studentRepo.findByEmail(email).stream().filter(x->x.getEmail().equals(email)).findAny().orElseThrow(()->new ResourceNotFoundException("Student", "email", email));
		return modelMapper.map(student, StudentDto.class);
	}

}
