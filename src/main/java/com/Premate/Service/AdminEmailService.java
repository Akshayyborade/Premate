package com.Premate.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.Premate.Exception.EmailException;
import com.Premate.Model.Admin;
import com.Premate.Model.AdminVerificationToken;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class AdminEmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private TemplateEngine templateEngine; 
    
    @Autowired
    private AdminVerificationTokenService adminVerificationTokenService;

    private static final Logger logger = LoggerFactory.getLogger(AdminEmailService.class);

    @Async
    public void sendVerificationEmail(String to, String token) throws EmailException {
    	// Assuming context path is obtained elsewhere (e.g., from an environment variable)
    	String contextPath = "http://localhost:9095"; 
    	String verificationPath = "/api/auth/verify-email?token="+token; // Separate verification path for clarity
        AdminVerificationToken byToken = adminVerificationTokenService.findByToken(token);
        Admin admin = byToken.getAdmin();
        
    	// Construct verification link with proper context path replacement
    	String verificationLink = contextPath + verificationPath;
    System.out.println(verificationLink);

    	// Craft informative and engaging email content with clear instructions
    	Context context = new Context();
    	context.setVariable("name",admin.getInstitutionName().toUpperCase()); // Assuming name can be extracted from email address
    	context.setVariable("verificationLink", verificationLink);
    	context.setVariable("companyName", "Premate Pvt. Ltd.");

    	String emailContent;
    	try {
    	    emailContent = templateEngine.process("email.html", context); // Ensure "email.html" path is correct
    	} catch (Exception e) {
    	    logger.error("Error processing email template: {}", e.getMessage());
    	    throw new EmailException("Failed to process email template", e);
    	}
// Replace placeholders

        // Create email message
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // Set to true for multipart/mixed
            helper.setTo(to);
            helper.setSubject("Verify Your Email Address");
            helper.setText(emailContent, true); // Set to true for HTML formatting

            // Send email asynchronously
            javaMailSender.send(message);

        } catch (MessagingException e) {
            // Log error with detailed message
            logger.error("Error sending verification email to {}: {}", to, e.getMessage());
            throw new EmailException("Error sending verification email", e);
        }
    }
}
