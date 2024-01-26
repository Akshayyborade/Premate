//package com.Premate.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.Premate.Model.Admin;
//import com.Premate.Repository.AdminRepo;
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private AdminRepo adminRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        List<Admin> adminList = adminRepository.findByEmail(username);
//        if (adminList.isEmpty()) {
//            throw new UsernameNotFoundException("Admin not found!");
//        }
//        Admin admin = adminList.get(0); // Assuming only one admin with the email
//        return new User(admin.getEmail(), admin.getPassword(),
//                admin.isEnabled(), true, true, true,
//                Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))); // Assign ADMIN role
//    }
//}
