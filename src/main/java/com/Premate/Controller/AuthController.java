package com.Premate.Controller;

import java.util.Date;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

import com.Premate.Exception.EmailException;
import com.Premate.Exception.UserNotVerifiedException;
import com.Premate.Model.Admin;
import com.Premate.Model.AdminVerificationToken;
import com.Premate.Model.AppUserRole;
import com.Premate.Security.JwtHelper;
import com.Premate.Service.AdminEmailService;
import com.Premate.Service.AdminServices;
import com.Premate.Service.AdminVerificationTokenService;
import com.Premate.Service.MyUserDetailsService;
import com.Premate.payload.AdminDto;
import com.Premate.util.ApiResponse;
import com.Premate.util.ApiResponseBuilder;
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
	public ResponseEntity<ApiResponse<Void>> createAdmin(@RequestBody AdminDto adminDto) throws EmailException {

	    // Fetch admin by email to check for existing accounts
	    String email = adminDto.getEmail();
	    AdminDto existingAdmin = adminServices.findByEmail(email);

	    if (existingAdmin != null) {
	        // Check if existing admin is enabled
	        boolean enabled = existingAdmin.isEnabled();
	        if (!enabled) {
	            // Look for verification token for the disabled admin
	            AdminVerificationToken adminVerificationToken = adminVerificationTokenService.findByAdmin(modelMapper.map(existingAdmin, Admin.class));
	            if (adminVerificationToken != null) {
	                // Admin exists, disabled, but has verification token (expected behavior)
	                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
	                        new ApiResponseBuilder<Void>()
	                                .setStatus(HttpStatus.FORBIDDEN)
	                                .setMessage("Your account is not verified. Please check your email and verify to proceed.")
	                                .build());
	            } else {
	                // Admin exists, disabled, and verification token is missing (unexpected scenario)
	                logger.error("Admin with email {} exists but verification token is not found.", email);
	                // Specific error response for missing verification token
	                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
	                        new ApiResponseBuilder<Void>()
	                                .setStatus(HttpStatus.FORBIDDEN)
	                                .setMessage("Verification token not found for this account. Please contact support.")
	                                .build());
	            }
	        } else {
	            // Admin exists and is already enabled, so prompt login
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(
	                    new ApiResponseBuilder<Void>()
	                            .setStatus(HttpStatus.CONFLICT)
	                            .setMessage("User email already registered. Please log in.")
	                            .build());
	        }
	    }

	    try {
	        // Process new admin registration
	        adminDto.setPassword(encoder.encode(adminDto.getPassword())); // Encode password for security
	        adminDto.setAppUserRole(AppUserRole.ADMIN); // Set default role (assuming ADMIN)

	        Admin admin = modelMapper.map(adminDto, Admin.class); // Convert DTO to Admin object

	        String token = UUID.randomUUID().toString(); // Generate unique verification token
	        Date expiryDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000); // Set token expiry in 1 day

	        // Create verification token and send verification email
	        adminVerificationTokenService.createVerificationToken(admin, token, expiryDate);
	        emailService.sendVerificationEmail(adminDto.getEmail(), token);

	        // Successful registration, return OK response with message
	        return ResponseEntity.ok(
	                new ApiResponseBuilder<Void>()
	                        .setStatus(HttpStatus.OK)
	                        .setMessage("Admin registered successfully. Verification email sent.")
	                        .build());
	    } catch (EmailException e) {
	        // Handle email sending error
	        logger.error("Error sending verification email:", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
	                new ApiResponseBuilder<Void>()
	                        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	                        .setMessage("Error sending verification email.")
	                        .build());
	    } catch (Exception e) {
	        // Handle any other exception during registration
	        logger.error("Error registering admin:", e);
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // Mark transaction for rollback
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
	                new ApiResponseBuilder<Void>()
	                        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	                        .setMessage("Error registering admin.")
	                        .build());
	    }

	    // This line was unreachable due to the previous return statements
	    // but is included for completeness to avoid potential issues
	    // return null; // Unreachable but added for clarity
	}

	@PostMapping("/adminLogin")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            
            // Load user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate tokens
            String accessToken = helper.generateAccessToken(userDetails);
            String refreshToken = helper.generateRefreshToken(userDetails);

            // Create login response
            Admin admin = (Admin) userDetails;
            LoginResponse response = new LoginResponse();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            response.setMessage("Login successful");
            response.setAdmin(modelMapper.map(admin, AdminDto.class));

            return ResponseEntity.ok(response);
        } catch (UserNotVerifiedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new LoginResponse("User not verified. Please verify your email to proceed.", null));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Invalid credentials", null));
        } catch (Exception ex) {
            logger.error("Login error:", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse("An error occurred during login", null));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid authorization header"));
            }

            String refreshToken = authHeader.substring(7);
            String username = helper.getUsernameFromToken(refreshToken);
            
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid refresh token"));
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (!helper.validateToken(refreshToken, userDetails)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid or expired refresh token"));
            }

            String newAccessToken = helper.generateAccessToken(userDetails);
            
            return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "message", "Token refreshed successfully"
            ));
        } catch (Exception e) {
            logger.error("Token refresh error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error refreshing token"));
        }
    }

	private boolean isValidEmail(String email) {
	    String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	    return email != null && email.matches(emailRegex);
	}

	@PostMapping("/check-email")
	public ResponseEntity<ApiResponse<Boolean>> checkEmail(@RequestParam String email) {
	    // Validate email format using a regex
	    if (!isValidEmail(email)) {
	        return ResponseEntity.badRequest().body(
	            new ApiResponse<>(400, "Invalid email format")
	        );
	    }

	    // Check if the email exists in the database (mocked logic here)
	    boolean emailExists = adminServices.findByEmail(email) != null;

	    if (emailExists) {
	        return ResponseEntity.ok(
	            new ApiResponse<>(200, "Email is already registered", emailExists)
	        );
	    } else {
	        return ResponseEntity.ok(
	            new ApiResponse<>(200, "Email is available")
	        );
	    }
	}


	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "Credentials Invalid !!";
	}

	@GetMapping("/verify-email")
	public ResponseEntity<String> verifyEmail(@RequestParam String token) {
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
		adminServices.updateAdminDto(modelMapper.map(admin, AdminDto.class), admin.getInstitutionId());

		// No need to create a new verification token here

		return ResponseEntity.ok("Email verified successfully");
	}
	
    //send Registration form to public 
	@PostMapping("/sendFormLink")
	public ResponseEntity<ApiResponse<Void>> sendFrormLink() {
		return null;
	}
	@GetMapping("/home")
	public String home() {
		return "hii ";
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
		try {
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				String token = authHeader.substring(7);
				// Add token to blacklist
				helper.invalidateToken(token);
				
				// Clear Spring Security Context
				SecurityContextHolder.clearContext();
				
				return ResponseEntity.ok(Map.of(
					"message", "Logged out successfully"
				));
			}
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Map.of("message", "Invalid authorization header"));
				
		} catch (Exception e) {
			logger.error("Logout error:", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("message", "Error during logout"));
		}
	}
}
