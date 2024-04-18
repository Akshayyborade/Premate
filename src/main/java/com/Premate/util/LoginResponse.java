package com.Premate.util;

import com.Premate.payload.AdminDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class LoginResponse {
  
	private String message;
    private AdminDto admin;
    private String jwtToken;
	public LoginResponse(String message, AdminDto admin) {
		super();
		this.message = message;
		this.admin = admin;
	}

    // Constructor, getters, and setters
    
}

