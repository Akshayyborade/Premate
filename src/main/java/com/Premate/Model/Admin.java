package com.Premate.Model;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Set;
//@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Admin implements UserDetails 
//implements UserDetails
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int institutionId;
	private String institutionName;
	@OneToOne(cascade = CascadeType.ALL)
	private Name owenerName;
	private String email;
	private String password;
	private String website;
	private String phone;
	private Date foundingDate;
	private String slogan;
	private String about;
	@Lob
	private byte[] profilePicture;
	@Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
	private boolean locked;
	private boolean enabled;
	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Student> students;

	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Teacher> teacher;

	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Grade> grade;
    @OneToOne(cascade = CascadeType.ALL)
	private Address address;
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
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(appUserRole.name()));
        // Add additional authorities from other sources if needed
        return authorities;
    }
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
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
	public boolean hasRole(AppUserRole role) {
        return appUserRole.equals(role);
    }

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		   System.out.println("User role working");
//	    return Arrays.asList(new SimpleGrantedAuthority(appUserRole.name()));
//	 
//	}
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//	    return email;
//	}
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//	
	

}
