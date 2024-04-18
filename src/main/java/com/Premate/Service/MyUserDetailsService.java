/*******************************************************************************
 * Premate- School Management System © 2024 by Akshay Borade is licensed under CC BY-NC-SA 4.0 
 *******************************************************************************/
package com.Premate.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Premate.Exception.UserNotVerifiedException;
import com.Premate.Model.Admin;
import com.Premate.Model.AdminVerificationToken;
import com.Premate.Repository.AdminRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private  AdminRepo adminRepo;
    @Autowired
    private AdminEmailService emailService;
    @Autowired
    private AdminVerificationTokenService tokenService;


  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Admin> admins = adminRepo.findByEmail(username);
        System.out.println(admins);
        // Assuming repository for Admin entity
        System.out.println("in user detail");
        if (admins.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        Admin admin = admins.get(0); // Assuming you're only interested in the first admin with the given email
        System.out.println(admin);
        System.out.println("in user detail");

        if (!admin.isEnabled()) {
            AdminVerificationToken adminVerificationToken = tokenService.findByAdmin(admin);
            emailService.sendVerificationEmail(admin.getEmail(), adminVerificationToken.getToken());
            throw new UserNotVerifiedException("Admin not verified");
        }
        // Check if account is locked before granting access
        if (admin.isLocked()) {
            throw new LockedException("Account is locked");
        }

        return admin;
    }

   

}
