package com.Premate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Premate.Model.Student;
import java.util.List;


public interface StudentRepo extends JpaRepository<Student,Integer> {

	
	List<Student> findByEmail(String email);
	

}
