package com.Premate.payload;

import java.sql.Date;

import com.Premate.Model.AppUserRole;

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
	private String email;
	private String password;
	private String website;
	private Date foundingDate;
	private String slogan;
	 private AppUserRole appUserRole;
		private boolean locked;
		private boolean enabled;
}
