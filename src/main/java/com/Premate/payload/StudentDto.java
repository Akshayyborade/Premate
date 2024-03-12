package com.Premate.payload;

import java.sql.Date;

import com.Premate.Model.Address;
import com.Premate.Model.Grade;
import com.Premate.Model.Name;
import com.Premate.Model.Parents;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * Data Transfer Object (DTO) class representing student data.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDto {

    /**
     * Unique identifier for the student.
     */
    private int stud_id;

    /**
     * The name of the student.
     */
    private Name studentName;

    /**
     * The name of the school the student is attending.
     */
    private String schoolName;

    /**
     * Mobile number of the student.
     */
    private String mobNumber;

    /**
     * Email address of the student.
     */
    private String email;

    /**
     * Password for the student.
     */
    private String password;

    /**
     * Gender of the student.
     */
    private String gender;

    /**
     * Date of birth of the student.
     */
    private Date dobDate;

    /**
     * The parent(s) of the student.
     */
    private Parents parents;

    /**
     * The grade of the student.
     */
    private Grade grade;
    
    /**
     * The address of the student.
     */
    private Address address;
}
