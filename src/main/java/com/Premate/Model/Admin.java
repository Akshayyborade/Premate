package com.Premate.Model;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"students", "teacher", "grade"})
public class Admin implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Lob
    private byte[] profilePicture;

    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    private boolean locked;
    private boolean enabled;

    /**
     * Bidirectional relationship with Student.
     * Added `mappedBy` to avoid foreign key redundancy.
     */
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Manage serialization for the forward side
    private List<Student> students;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Teacher> teacher;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Grade> grade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", unique = true)
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
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean hasRole(AppUserRole role) {
        return appUserRole.equals(role);
    }
}
