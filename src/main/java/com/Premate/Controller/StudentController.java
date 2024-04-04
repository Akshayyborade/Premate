package com.Premate.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Premate.Service.AddressService;
import com.Premate.Service.AdminServices;
import com.Premate.Service.StudentService;
import com.Premate.payload.StudentDto;
import com.Premate.util.StudentRegistrationRequest;

import org.springframework.web.bind.annotation.PutMapping;


/**
 * Controller class for managing student-related operations.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    @Autowired
    private AddressService addressService;
   
    /**
     * Endpoint for registering a new student.
     *
     * @param studentDto The data of the student to be registered.
     * @return ResponseEntity with the created student and HTTP status.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentRegistrationRequest studentData){
    	
        System.out.println(studentData);
      
        
       
        
        
        StudentDto stud = studentService.createStud(studentData.getStudentDto(), studentData.getAdminId());
//        addressService.saveAddressByStudentID(stud.getAddress(),stud.getStud_id());
        
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
        try {
            List<StudentDto> allStud = studentService.getAllStud();
            System.out.println("fetching all student details  "+ allStud);
            return ResponseEntity.ok(allStud);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    public ResponseEntity<StudentDto> updateStudent( @RequestBody StudentDto studentDto,@PathVariable int id) {
        StudentDto updateStud = studentService.updateStud(studentDto, id);
        return new ResponseEntity<StudentDto>(updateStud, HttpStatus.CREATED);
    }
}
