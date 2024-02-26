package com.Premate.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AdminEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendVerificationEmail(String to, String token) {
        String verificationLink = "http://localhost:9095/api/auth/verify-email?token=" + token;
        String emailContent = "Click the following link to verify your email address: " + verificationLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Email Verification");
        message.setText(emailContent);

        javaMailSender.send(message);
    }
}
