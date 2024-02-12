package com.Premate.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.Premate.Model.AppUserRole;
import com.Premate.Model.LoginRequest;
import com.Premate.Service.AdminServices;
import com.Premate.payload.AdminDto;

@RestController()
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

   
    @Autowired
	private AdminServices adminServices;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    // http://localhost:9095/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) {
        // Create the admin entity from the DTO
        adminDto.setPassword(encoder.encode(adminDto.getPassword()));
        adminDto.setAppUserRole(AppUserRole.ADMIN);
        adminDto.setEnabled(true);

        // Save the admin entity (implementation depends on your service)
        AdminDto dto = adminServices.createAdmin(adminDto);

        // Return the response
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }@CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/adminLogin")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
    	System.out.println(loginRequest.getEmail());
        try {
            Authentication authentication = (Authentication) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication( authentication);

            // Generate JWT or custom security token (not included in this example)

            return ResponseEntity.ok("Login successful");
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }
	@GetMapping("/home")
	public String home() {
		return "hii ";
	}
}
