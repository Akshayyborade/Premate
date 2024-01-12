package com.Premate.Model;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Admin implements UserDetails{

	 @SequenceGenerator(
	            name = "admin_sequence",
	            sequenceName = "admin_sequence",
	            allocationSize = 1
	    )
	    @Id
	    @GeneratedValue(
	            strategy = GenerationType.SEQUENCE,
	            generator = "admin_sequence"
	    )
	private int institutionId;
	private String institutionName;
	private String email;
	private String password;
	private String website;
	private Date foundingDate;
	private String slogan;
	@Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
	private boolean locked;
	private boolean enabled;
	public Admin(String institutionName, String email, String password, String website, Date foundingDate,
			String slogan, AppUserRole appUserRole) {
		super();
		this.institutionName = institutionName;
		this.email = email;
		this.password = password;
		this.website = website;
		this.foundingDate = foundingDate;
		this.slogan = slogan;
		this.appUserRole = appUserRole;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		   System.out.println("User role working");
	    return Arrays.asList(new SimpleGrantedAuthority(appUserRole.name()));
	 
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
	    return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}