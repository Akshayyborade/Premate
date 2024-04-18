package com.Premate.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Premate.Model.Teacher;


public interface TeacherRepo extends JpaRepository<Teacher, Integer> {

	List<Teacher> findByEmail(String email);
	

}
