package com.Premate.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Premate.Model.Admin;
import com.Premate.Repository.AdminRepo;
@Service
public class MyUserDetailsService implements UserDetailsService {

	 @Autowired
	    private AdminRepo adminRepository; // Assuming a repository for Admin entity

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    	System.out.println("load user by email");
	        List<Admin> adminList = adminRepository.findByEmail(username);
	        Admin admin = adminList.get(0);// Assuming email is the username
	        System.out.println(admin);
	        if (admin == null) {
	            throw new UsernameNotFoundException("Admin not found!");
	        }
	        return admin;
	    }

}
