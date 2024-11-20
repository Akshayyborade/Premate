package com.Premate.Security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Component
public class JwtHelper {
    // Access token validity: 15 minutes
    public static final long ACCESS_TOKEN_VALIDITY = 15 * 60;
    // Refresh token validity: 7 days
    public static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60;

    @Value("${jwt.secret.access}")
    private String accessTokenSecret;

    @Value("${jwt.secret.refresh}")
    private String refreshTokenSecret;

    // Convert string secrets to SecretKey objects
    private SecretKey getAccessSecretKey() {
        return Keys.hmacShaKeyFor(accessTokenSecret.getBytes());
    }

    private SecretKey getRefreshSecretKey() {
        return Keys.hmacShaKeyFor(refreshTokenSecret.getBytes());
    }

    // Generate access token
    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY * 1000))
            .signWith(getAccessSecretKey())
            .compact();
    }

    // Generate refresh token
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY * 1000))
            .signWith(getRefreshSecretKey())
            .compact();
    }

    // Simplified method to get username from any token
    public String getUsernameFromToken(String token) {
        try {
            // Try access token first
            return getUsernameFromToken(token, accessTokenSecret);
        } catch (Exception e) {
            // If that fails, try refresh token
            return getUsernameFromToken(token, refreshTokenSecret);
        }
    }

    // General token validation method
    public Boolean validateToken(String token, UserDetails userDetails) {
        if (tokenBlacklist.contains(token)) {
            return false;
        }
        try {
            Claims claims = getAllClaimsFromToken(token, accessTokenSecret);
            String tokenType = (String) claims.get("type");
            
            if ("access".equals(tokenType)) {
                return validateAccessToken(token, userDetails);
            } else if ("refresh".equals(tokenType)) {
                return validateRefreshToken(token, userDetails);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // Private helper methods for token validation
    private Boolean validateTokenCommon(String token, UserDetails userDetails, String secret, String expectedType) {
        try {
            final String username = getUsernameFromToken(token, secret);
            Claims claims = getAllClaimsFromToken(token, secret);
            
            return (username.equals(userDetails.getUsername()) && 
                   !isTokenExpired(claims) && 
                   expectedType.equals(claims.get("type")));
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean validateAccessToken(String token, UserDetails userDetails) {
        return validateTokenCommon(token, userDetails, accessTokenSecret, "access");
    }

    private Boolean validateRefreshToken(String token, UserDetails userDetails) {
        return validateTokenCommon(token, userDetails, refreshTokenSecret, "refresh");
    }

    // Get expiration date from token
    public Date getExpirationDateFromToken(String token, String secret) {
        return getClaimFromToken(token, Claims::getExpiration, secret);
    }

    // Get claim from token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, String secret) {
        final Claims claims = getAllClaimsFromToken(token, secret);
        return claimsResolver.apply(claims);
    }

    // Get all claims from token
    private Claims getAllClaimsFromToken(String token, String secret) {
        SecretKey key = secret.equals(accessTokenSecret) ? 
            getAccessSecretKey() : getRefreshSecretKey();
            
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // Check if token is expired
    private Boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    // Refresh access token using refresh token
    public String refreshAccessToken(String refreshToken, UserDetails userDetails) {
        if (validateRefreshToken(refreshToken, userDetails)) {
            return generateAccessToken(userDetails);
        }
        throw new RuntimeException("Invalid refresh token");
    }

    // Add this new method
    private String getUsernameFromToken(String token, String secret) {
        return getClaimFromToken(token, Claims::getSubject, secret);
    }

    // Add to JwtHelper class
    private Set<String> tokenBlacklist = new HashSet<>();

    public void invalidateToken(String token) {
        tokenBlacklist.add(token);
    }
}