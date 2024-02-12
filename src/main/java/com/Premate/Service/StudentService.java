package com.Premate.Service;

import java.util.List;

import com.Premate.payload.StudentDto;

/**
 * Service interface for managing student-related operations.
 */
public interface StudentService {

    /**
     * Create a new student.
     *
     * @param studentDto The data of the student to be created.
     * @return The created student as a DTO.
     */
    StudentDto createStud(StudentDto studentDto);

    /**
     * Update an existing student.
     *
     * @param studentDto The updated data for the student.
     * @param id         The ID of the student to be updated.
     * @return The updated student as a DTO.
     */
    StudentDto updateStud(StudentDto studentDto, int id);

    /**
     * Delete a student by ID.
     *
     * @param id The ID of the student to be deleted.
     */
    void deleteStud(int id);

    /**
     * Get a student by ID.
     *
     * @param id The ID of the student to be retrieved.
     * @return The retrieved student as a DTO.
     */
    StudentDto getStud(int id);

    /**
     * Get all students.
     *
     * @return List of all students as DTOs.
     */
    List<StudentDto> getAllStud();

    /**
     * Get a student by email address.
     *
     * @param email The email address of the student.
     * @return The retrieved student as a DTO.
     */
    StudentDto getStudByEmail(String email);
}
