package com.Premate.Model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"admin", "attendance", "results", "batch", "exam", "timetable"})
public class Student implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int stud_id;

    @OneToOne(cascade = CascadeType.ALL)
    private Name studentName;

    private String schoolName;
    private String mobNumber;
    private String email;
    private String password;
    private String gender;
    private Date dobDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Parents parents;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gradeId")
    private Grade grade;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<Attendance> attendance;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonBackReference
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToMany(mappedBy = "students")
    private List<Exam> exam;

    @ManyToOne(cascade = CascadeType.ALL)
    private Timetable timetable;

    @OneToMany(mappedBy = "student")
    private List<Result> results;

    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;

    /**
     * Role of the student (e.g., ROLE_STUDENT).
     */
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole = AppUserRole.STUDENT;

    
    private Boolean isactive = Boolean.TRUE;  // Use Boolean instead of boolean

   
    private LocalDate dateOfAddmission;

    /**
     * Spring Security integration - Methods for UserDetails implementation
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Returning the student's role as a GrantedAuthority.
        return List.of(new SimpleGrantedAuthority(appUserRole.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isactive;
    }
}
