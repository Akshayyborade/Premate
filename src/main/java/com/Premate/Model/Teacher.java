package com.Premate.Model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a Teacher.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher {

    /**
     * Unique identifier for the teacher.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int teacher_id;

    /**
     * Name of the teacher.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Name teacherName;

    /**
     * Date of birth of the teacher.
     */
    private Date dobDate;

    /**
     * Mobile number of the teacher.
     */
    private String mobNum;

    /**
     * Email address of the teacher.
     */
    private String email;

    /**
     * Password for accessing teacher-related functionalities.
     */
    private String password;

    /**
     * Aadhar number of the teacher.
     */
    private int adharNum;

    /**
     * Experience details of the teacher.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private Experience experience;

    /**
     * Education details of the teacher.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private Education education;
    
    /**
     * Reference to the attendance of the teacher.
     */
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "teacher")
    private List<Attendance> attendance;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")
    private Admin admin;
    
    
    
    /**
     * Role of the teacher in the application.
     */
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
}

