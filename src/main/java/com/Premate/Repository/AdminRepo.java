package com.Premate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Premate.Model.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {
	

}
