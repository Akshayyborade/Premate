package com.Premate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Premate.Model.Admin;
import com.Premate.Model.AdminVerificationToken;
import java.util.List;


public interface AdminVerificationTokenRepository extends JpaRepository<AdminVerificationToken, Long>  {
List<AdminVerificationToken> findByToken(String token);
List<AdminVerificationToken> findByAdmin(Admin admin);
}