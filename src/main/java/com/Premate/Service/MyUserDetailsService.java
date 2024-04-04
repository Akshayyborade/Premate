package com.Premate.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        Admin admin = adminRepo.findByEmail(username).get(0);
        System.out.println(admin);
        // Assuming repository for Admin entity
        System.out.println("in user detail");

        if (admin == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        if(!admin.isEnabled()) {
        	AdminVerificationToken  adminVerificationToken = tokenService.findByAdmin(admin);
        	emailService.sendVerificationEmail(admin.getEmail(),adminVerificationToken.getToken() );
        	throw new UserNotVerifiedException("Admin not verified");
        }

        // Check if account is locked before granting access
        if (admin.isLocked()) {
            throw new LockedException("Account is locked");
        }

        // Consider using a PasswordEncoder interface implementation for password security
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // Example encoder
//        if (!encoder.matches(password, admin.getPassword())) {
//            throw new BadCredentialsException("Invalid credentials");
//        }

        // Construct and return UserPrincipal with authorities
        return admin;
    }

   

}
