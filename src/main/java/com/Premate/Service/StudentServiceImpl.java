package com.Premate.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Premate.Exception.ResourceNotFoundException;
import com.Premate.Model.Grade;
import com.Premate.Model.Name;
import com.Premate.Model.Parents;
import com.Premate.Model.Student;
import com.Premate.Repository.GradeRepo;
import com.Premate.Repository.NameRepo;
import com.Premate.Repository.ParentsRepo;
import com.Premate.Repository.StudentRepo;
import com.Premate.payload.StudentDto;

/**
 * Service implementation for managing student-related operations.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NameRepo nameRepo;

    @Autowired
    private GradeRepo gradeRepo;

    @Autowired
    private ParentsRepo parentsRepo;

    /**
     * Create a new student.
     *
     * @param studentDto The data of the student to be created.
     * @return The created student as a DTO.
     */
    @Override
    public StudentDto createStud(StudentDto studentDto) {
        Name name = studentDto.getStudentName();
        nameRepo.save(name);

        Grade grade = studentDto.getGrade();
        gradeRepo.save(grade);

        Parents parents = studentDto.getParents();
        parentsRepo.save(parents);

        Student save = studentRepo.save(modelMapper.map(studentDto, Student.class));
        return modelMapper.map(save, StudentDto.class);
    }

    /**
     * Update an existing student.
     *
     * @param studentDto The updated data for the student.
     * @param id         The ID of the student to be updated.
     * @return The updated student as a DTO.
     */
    @Override
    public StudentDto updateStud(StudentDto studentDto, int id) {
        Student student = studentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student", "StudentId", id));
        studentDto.setStud_id(student.getStud_id());
        modelMapper.map(studentDto, student);
        Student save = studentRepo.save(student);
        return modelMapper.map(save, StudentDto.class);
    }

    /**
     * Delete a student by ID.
     *
     * @param id The ID of the student to be deleted.
     */
    @Override
    public void deleteStud(int id) {
        studentRepo.deleteById(id);
    }

    /**
     * Get a student by ID.
     *
     * @param id The ID of the student to be retrieved.
     * @return The retrieved student as a DTO.
     */
    @Override
    public StudentDto getStud(int id) {
        Student student = studentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student", "StudentId", id));
        return modelMapper.map(student, StudentDto.class);
    }

    /**
     * Get all students.
     *
     * @return List of all students as DTOs.
     */
    @Override
    public List<StudentDto> getAllStud() {
        List<StudentDto> studentDtos = studentRepo.findAll().stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
        return studentDtos;
    }

    /**
     * Get a student by email address.
     *
     * @param email The email address of the student.
     * @return The retrieved student as a DTO.
     */
    @Override
    public StudentDto getStudByEmail(String email) {
        Student student = studentRepo.findByEmail(email).stream()
                .filter(x -> x.getEmail().equals(email))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Student", "email", email));
        return modelMapper.map(student, StudentDto.class);
    }
}
