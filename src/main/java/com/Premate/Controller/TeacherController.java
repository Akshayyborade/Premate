package com.Premate.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Premate.payload.TeacherDto;
import com.Premate.Service.TeacherService;

/**
 * REST controller for managing teacher-related operations.
 */
@RestController
@RequestMapping("/api/teachers") // Base path for teacher endpoints
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * Creates a new teacher.
     *
     * @param teacherDto The TeacherDto containing information for the new teacher.
     * @return The created TeacherDto.
     */
    @PostMapping // Create a new teacher
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto teacherDto) {
        TeacherDto createdTeacher = teacherService.createTeacher(teacherDto);
        return new ResponseEntity<>(createdTeacher, HttpStatus.CREATED);
    }

    /**
     * Updates an existing teacher.
     *
     * @param id         The ID of the teacher to update.
     * @param teacherDto The TeacherDto containing updated information for the teacher.
     * @return The updated TeacherDto.
     */
    @PutMapping("/{id}") // Update an existing teacher
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable int id, @RequestBody TeacherDto teacherDto) {
        TeacherDto updatedTeacher = teacherService.updateTeacher(teacherDto, id);
        return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
    }

    /**
     * Retrieves a teacher by ID.
     *
     * @param id The ID of the teacher to retrieve.
     * @return The TeacherDto representing the retrieved teacher.
     */
    @GetMapping("/{id}") // Retrieve a teacher by ID
    public ResponseEntity<TeacherDto> getTeacher(@PathVariable int id) {
        TeacherDto teacherDto = teacherService.getTeacher(id);
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }

    /**
     * Retrieves all teachers.
     *
     * @return List of TeacherDto representing all teachers.
     */
    @GetMapping // Retrieve all teachers
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        List<TeacherDto> teacherDtos = teacherService.getTeacherDtos();
        return new ResponseEntity<>(teacherDtos, HttpStatus.OK);
    }

    /**
     * Deletes a teacher by ID.
     *
     * @param id The ID of the teacher to delete.
     */
    @DeleteMapping("/{id}") // Delete a teacher by ID
    public ResponseEntity<Void> deleteTeacher(@PathVariable int id) {
        teacherService.deleteTeacher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves a teacher by email.
     *
     * @param email The email of the teacher to retrieve.
     * @return The TeacherDto representing the retrieved teacher.
     */
    @GetMapping("/email/{email}") // Retrieve a teacher by email
    public ResponseEntity<TeacherDto> findByEmail(@PathVariable String email) {
        TeacherDto teacherDto = teacherService.findByEmail(email);
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }
}
