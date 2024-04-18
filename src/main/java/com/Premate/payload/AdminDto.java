/*******************************************************************************
 * Premate- School Management System © 2024 by Akshay Borade is licensed under CC BY-NC-SA 4.0 
 *******************************************************************************/
package com.Premate.payload;

import java.sql.Date;

import com.Premate.Model.Address;
import com.Premate.Model.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminDto {
	private int institutionId;
	private String institutionName;
	private String ownerName;
	private String email;
	private String password;
	private String website;
	private String phone;
	private Date foundingDate;
	private String slogan;
	private String about;
	private String base64Image;
	// Custom converter to convert JSON string to Address object
	private Address address;

//	private byte[] profilePicture;
	private AppUserRole appUserRole;
	private boolean locked;
	private boolean enabled;
	
}
