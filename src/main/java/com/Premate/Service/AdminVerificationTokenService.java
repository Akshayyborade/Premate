package com.Premate.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Premate.Model.Admin;
import com.Premate.Model.AdminVerificationToken;
import com.Premate.Repository.AdminVerificationTokenRepository;

@Service
public class AdminVerificationTokenService {
    @Autowired
    private AdminVerificationTokenRepository adminVerificationTokenRepository;

    public void createVerificationToken(Admin admin, String token, Date expDate) {
        AdminVerificationToken adminVerificationToken = new AdminVerificationToken(token, admin, expDate );
        adminVerificationTokenRepository.save(adminVerificationToken);
    }

    public AdminVerificationToken findByToken(String token) {
        List<AdminVerificationToken> tokens = adminVerificationTokenRepository.findByToken(token);
        if (!tokens.isEmpty()) {
            return tokens.get(0); // Return the first token if found
        } else {
            return null; // Return null if no token is found
        }
    }
    public AdminVerificationToken findByAdmin(Admin admin) {
    	List<AdminVerificationToken> byAdmin = adminVerificationTokenRepository.findByAdmin(admin);
    	if (byAdmin != null) {
    		return byAdmin.get(0);
			
		}else {
			return null;
		}
    }

}
