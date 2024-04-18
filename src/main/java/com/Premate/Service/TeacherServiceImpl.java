package com.Premate.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.Premate.Exception.ResourceNotFoundException;
import com.Premate.Model.Education;
import com.Premate.Model.Experience;
import com.Premate.Model.Teacher;
import com.Premate.Repository.EducationRepo;
import com.Premate.Repository.ExperienceRepo;
import com.Premate.Repository.TeacherRepo;
import com.Premate.payload.TeacherDto;
@Service
public class TeacherServiceImpl implements TeacherService {
	@Autowired
	private TeacherRepo teacherRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private EducationRepo educationRepo;
	@Autowired
	private ExperienceRepo experienceRepo;
	

	/**
	 * Creates a new teacher based on the provided TeacherDto.
	 *
	 * @param teacherDto The TeacherDto containing information for the new teacher.
	 * @return The created TeacherDto.
	 * @throws IllegalArgumentException If the provided TeacherDto is null.
	 * @throws RuntimeException         If there is an error during the teacher saving process.
	 */
	@Override
	public TeacherDto createTeacher(TeacherDto teacherDto) {
	    // Check if the provided TeacherDto is null
	    if (teacherDto == null) {
	        throw new IllegalArgumentException("TeacherDto cannot be null");
	    }

	    // Map the TeacherDto to the Teacher entity
	    Teacher teacherToSave = modelMapper.map(teacherDto, Teacher.class);
	    
	    //saving Education
	    Education education= teacherDto.getEducation();
	    
	    //saving experience
	    Experience experience = teacherDto.getExperience();

	    // Save the teacher to the database
	    Teacher savedTeacher;
	    try {
	    	educationRepo.save(education);
	    	experienceRepo.save(experience);
	        savedTeacher = teacherRepo.save(teacherToSave);
	    } catch (Exception e) {
	        // Handle any runtime exception during the save process
	        throw new RuntimeException("Error saving teacher", e);
	    }

	    // Check if the savedTeacher is null (unexpected scenario)
	    if (savedTeacher == null) {
	        throw new RuntimeException("Unexpected: Saved teacher is null");
	    }

	    // Map the saved Teacher entity back to TeacherDto and return
	    return modelMapper.map(savedTeacher, TeacherDto.class);
	}


	/**
	 * Updates an existing teacher identified by the given ID with the information provided in the TeacherDto.
	 *
	 * @param teacherDto The TeacherDto containing updated information for the teacher.
	 * @param id         The ID of the teacher to be updated.
	 * @return The updated TeacherDto.
	 * @throws IllegalArgumentException      If the provided TeacherDto is null or the ID is invalid.
	 * @throws ResourceNotFoundException     If the teacher with the given ID is not found.
	 * @throws RuntimeException              If there is an error during the teacher update process.
	 */
	@Override
	public TeacherDto updateTeacher(TeacherDto teacherDto, int id) {
	    // Check if the provided TeacherDto is null or the ID is invalid
	    if (teacherDto == null) {
	        throw new IllegalArgumentException("TeacherDto cannot be null");
	    }

	    // Retrieve the existing teacher from the database
	    Optional<Teacher> existingTeacherOptional = teacherRepo.findById(id);

	    // Check if the teacher with the given ID exists
	    if (existingTeacherOptional.isEmpty()) {
	        throw new ResourceNotFoundException("Teacher", "ID", id);
	    }

	    // Map the updated information from TeacherDto to the existing Teacher entity
	    Teacher existingTeacher = existingTeacherOptional.get();
	    modelMapper.map(teacherDto, existingTeacher);

	    // Save the updated teacher to the database
	    Teacher updatedTeacher;
	    try {
	        updatedTeacher = teacherRepo.save(existingTeacher);
	    } catch (Exception e) {
	        // Handle any runtime exception during the update process
	        throw new RuntimeException("Error updating teacher", e);
	    }

	    // Check if the updatedTeacher is null (unexpected scenario)
	    if (updatedTeacher == null) {
	        throw new RuntimeException("Unexpected: Updated teacher is null");
	    }

	    // Map the updated Teacher entity back to TeacherDto and return
	    return modelMapper.map(updatedTeacher, TeacherDto.class);
	}


	/**
	 * Retrieves the teacher with the given ID.
	 *
	 * @param id The ID of the teacher to be retrieved.
	 * @return The TeacherDto representing the retrieved teacher.
	 * @throws IllegalArgumentException  If the provided ID is invalid.
	 * @throws ResourceNotFoundException If the teacher with the given ID is not found.
	 */
	@Override
	public TeacherDto getTeacher(int id) {
	    // Check if the provided ID is invalid
	    if (id <= 0) {
	        throw new IllegalArgumentException("Invalid teacher ID");
	    }

	    // Retrieve the teacher from the database
	    Optional<Teacher> teacherOptional = teacherRepo.findById(id);

	    // Check if the teacher with the given ID exists
	    if (teacherOptional.isEmpty()) {
	        throw new ResourceNotFoundException("Teacher", "ID", id);
	    }

	    // Map the retrieved Teacher entity to TeacherDto and return
	    return modelMapper.map(teacherOptional.get(), TeacherDto.class);
	}


	/**
	 * Retrieves a list of all teachers.
	 *
	 * @return List of TeacherDto representing all teachers.
	 */
	@Override
	public List<TeacherDto> getTeacherDtos() {
	    List<Teacher> teachers = teacherRepo.findAll();
	    return teachers.stream()
	            .map(teacher -> modelMapper.map(teacher, TeacherDto.class))
	            .collect(Collectors.toList());
	}


	/**
	 * Deletes the teacher with the given ID.
	 *
	 * @param id The ID of the teacher to be deleted.
	 * @throws IllegalArgumentException  If the provided ID is invalid.
	 * @throws ResourceNotFoundException If the teacher with the given ID is not found.
	 */
	@Override
	public void deleteTeacher(int id) {
	    // Check if the provided ID is invalid
	    if (id <= 0) {
	        throw new IllegalArgumentException("Invalid teacher ID");
	    }

	    // Check if the teacher with the given ID exists
	    if (!teacherRepo.existsById(id)) {
	        throw new ResourceNotFoundException("Teacher", "ID", id);
	    }

	    // Delete the teacher from the database
	    teacherRepo.deleteById(id);
	}


	/**
	 * Retrieves the teacher with the given email.
	 *
	 * @param email The email of the teacher to be retrieved.
	 * @return The TeacherDto representing the retrieved teacher.
	 * @throws IllegalArgumentException  If the provided email is invalid.
	 * @throws ResourceNotFoundException If the teacher with the given email is not found.
	 */
	@Override
	public TeacherDto findByEmail(String email) {
	    // Check if the provided email is invalid
	    if (ObjectUtils.isEmpty(email)) {
	        throw new IllegalArgumentException("Invalid teacher email");
	    }

	    // Retrieve the list of teachers from the database by email
	    List<Teacher> teachers = teacherRepo.findByEmail(email);

	    // Check if the list is empty, indicating no teacher found
	    if (CollectionUtils.isEmpty(teachers)) {
	        throw new ResourceNotFoundException("Teacher", "Email", email);
	    }

	    // Assuming you want to handle a case where multiple teachers have the same email,
	    // you might want to consider additional logic here, such as choosing the first teacher in the list.

	    // Map the retrieved Teacher entity to TeacherDto and return
	    return modelMapper.map(teachers.get(0), TeacherDto.class);
	}


}
