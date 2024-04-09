package com.Premate.payload;

import java.sql.Date;

import com.Premate.Model.Address;
import com.Premate.Model.AppUserRole;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	private Address address;

//	private byte[] profilePicture;
	private AppUserRole appUserRole;
	private boolean locked;
	private boolean enabled;
	
}
