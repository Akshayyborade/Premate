package com.Premate.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.Premate.Model.Admin;
import com.Premate.Model.AdminVerificationToken;
import com.Premate.Model.AppUserRole;
import com.Premate.Security.JwtHelper;
import com.Premate.Service.AdminEmailService;
import com.Premate.Service.AdminServices;
import com.Premate.Service.AdminVerificationTokenService;
import com.Premate.Service.MyUserDetailsService;
import com.Premate.payload.AdminDto;
import com.Premate.util.LoginRequest;
import com.Premate.util.LoginResponse;

@RestController()
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private AdminEmailService emailService;
	@Autowired
	private AdminVerificationTokenService adminVerificationTokenService;
	@Autowired
	private AdminServices adminServices;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	private JwtHelper helper;
	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	// http://localhost:9095/api/auth/signup
	@PostMapping("/signup")
	@Transactional
	public ResponseEntity<String> createAdmin(@RequestBody AdminDto adminDto) {
		try {
			// Validate admin DTO

			// Encode password
			adminDto.setPassword(encoder.encode(adminDto.getPassword()));

			// Set role
			adminDto.setAppUserRole(AppUserRole.ADMIN);
//			adminServices.createAdmin(adminDto); //dupllication occur

			// Generate token
			String token = UUID.randomUUID().toString();

			// Save admin

			Admin admin = modelMapper.map(adminDto, Admin.class);

			Date expiryDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000); // 1 day from now

			// Create verification token
			adminVerificationTokenService.createVerificationToken(admin, token, expiryDate);

			// Send verification email
			emailService.sendVerificationEmail(adminDto.getEmail(), token);

			return ResponseEntity.ok("Admin registered successfully. Verification email sent.");
		} catch (Exception e) {
			// Log error
			e.printStackTrace();
			// Rollback transaction
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering admin.");
		}
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/adminLogin")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		System.out.println(loginRequest.getEmail());
		try {
//			Authentication authentication = (Authentication) authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//			Admin admin = (Admin) authentication.getPrincipal();
//			AdminVerificationToken adminVerificationToken = adminVerificationTokenService.findByAdmin(admin);
//			String token = adminVerificationToken.getToken();

			// Generate JWT or custom security token (not included in this example)
			Admin admin =  this.doAuthenticate(loginRequest.getEmail(), loginRequest.getPassword());
			UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
			//genrate token on login 
			String token = this.helper.generateToken(userDetails);
			LoginResponse response = new LoginResponse();
			response.setJwtToken(token);
			response.setMessage("Login successful");
			response.setAdmin(modelMapper.map(admin, AdminDto.class));
		
			
//			response.setMessage("Login successful");
//			response.setAdmin(modelMapper.map(admin, AdminDto.class));
//			response.setToken(token);

			return ResponseEntity.ok(response);
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new LoginResponse("Invalid username or password", null));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new LoginResponse("Login failed", null));
		}
	}

	private Admin doAuthenticate(String email, String password) {
		Admin admin = new Admin();
		try {
			Authentication authentication = (Authentication) authenticationManager.authenticate (new UsernamePasswordAuthenticationToken(email,password));
			admin=(Admin) authentication.getPrincipal();
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}
		return admin;
	}

	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "Credentials Invalid !!";
	}

	@GetMapping("/verify-email")
	public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
		AdminVerificationToken adminVerificationToken = adminVerificationTokenService.findByToken(token);
		if (adminVerificationToken == null || adminVerificationToken.getExpiryDate() == null) {
			return ResponseEntity.badRequest().body("Invalid token or token has no expiry date");
		}

		// Check if the token has expired
		Date expiryDate = adminVerificationToken.getExpiryDate();
		if (expiryDate != null && new Date().after(expiryDate)) {
			return ResponseEntity.badRequest().body("Token has expired");
		}

		Admin admin = adminVerificationToken.getAdmin();
		System.out.println(admin);
		admin.setEnabled(true);

		// Save the updated Admin entity to enable the account
		adminServices.updateAdmin(modelMapper.map(admin, AdminDto.class), admin.getInstitutionId());

		// No need to create a new verification token here

		return ResponseEntity.ok("Email verified successfully");
	}

	@GetMapping("/home")
	public String home() {
		return "hii ";
	}
}
