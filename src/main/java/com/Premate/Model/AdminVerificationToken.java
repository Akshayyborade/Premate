// VerificationToken.java
package com.Premate.Model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdminVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", referencedColumnName = "institutionId")
    private Admin admin;

    private Date expiryDate;

	public AdminVerificationToken(String token, Admin admin) {
		super();
		this.token = token;
		this.admin = admin;
	}

	public AdminVerificationToken(String token, Admin admin, Date expiryDate) {
		super();
		this.token = token;
		this.admin = admin;
		this.expiryDate = expiryDate;
	}

    // Constructors, getters, setters
}
