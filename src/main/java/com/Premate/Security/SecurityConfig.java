//package com.Premate.Security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity // Keep this for method-level security
//public class SecurityConfig {
//
//	@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.authorizeHttpRequests(authorize -> authorize
//                // Permit static resources and login pages
//                .requestMatchers("/adminlogin", "/admin_register", "/", "css/**", "img/**", "/adminReg", "/adminLogin")
//                .permitAll()
//                // Restrict /home to ADMIN role
//                .requestMatchers("/home").hasRole("ADMIN")
//                // Authenticate all other requests
//                .anyRequest().authenticated())
//                .formLogin(login -> login.loginPage("/adminlogin")
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .loginProcessingUrl("/adminLogin")
//                        .defaultSuccessUrl("/home")
//                        .failureUrl("/login?error"))
//                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout"))
//                .build();
//    }
//
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//		System.out.println("Authentication manager is working");
//		return configuration.getAuthenticationManager();
//	}
//
//}
