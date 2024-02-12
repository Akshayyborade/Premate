package com.Premate.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Premate.Service.StudentService;
import com.Premate.payload.StudentDto;
import org.springframework.web.bind.annotation.PutMapping;


/**
 * Controller class for managing student-related operations.
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    /**
     * Endpoint for registering a new student.
     *
     * @param studentDto The data of the student to be registered.
     * @return ResponseEntity with the created student and HTTP status.
     */
    @PostMapping("/register")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto){
        System.out.println(studentDto.toString());
        StudentDto stud = studentService.createStud(studentDto);
        return new ResponseEntity<StudentDto>(stud, HttpStatus.CREATED);
    }

    /**
     * Endpoint for retrieving a student by ID.
     *
     * @param studentId The ID of the student to be retrieved.
     * @return ResponseEntity with the retrieved student and HTTP status.
     */
    @GetMapping("/getStudent/{studentId}")
    public ResponseEntity<StudentDto> getStudents(@PathVariable int studentId){
        return new ResponseEntity<StudentDto>(studentService.getStud(studentId), HttpStatus.FOUND);
    }

    /**
     * Endpoint for retrieving all students.
     *
     * @return ResponseEntity with a list of all students and HTTP status.
     */
    @GetMapping("/getAllStudent")
    public ResponseEntity<List<StudentDto>> getAllStudents(){
        List<StudentDto> allStud = studentService.getAllStud();
        return new ResponseEntity<List<StudentDto>>(allStud, HttpStatus.FOUND);
    }

    /**
     * Endpoint for deleting a student by ID.
     *
     * @param studId The ID of the student to be deleted.
     * @return ResponseEntity with a success message and HTTP status.
     */
    @DeleteMapping("/deleteStudent/{studId}")
    public ResponseEntity<String> deleteStudent(@PathVariable int studId){
        studentService.deleteStud(studId);
        return new ResponseEntity<String>("Student Deleted Successfully", HttpStatus.OK);
    }

    /**
     * Endpoint for updating a student by ID.
     *
     * @param id The ID of the student to be updated.
     * @param studentDto The updated data for the student.
     * @return ResponseEntity with the updated student and HTTP status.
     */
    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable int id, @RequestBody StudentDto studentDto) {
        StudentDto updateStud = studentService.updateStud(studentDto, id);
        return new ResponseEntity<StudentDto>(updateStud, HttpStatus.CREATED);
    }
}
