package com.smartsense.config;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartsense.exceptions.InvalidDataException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTGenerator {

    private SecurityConstants securityConstants;

    public JWTGenerator(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + securityConstants.getJwtExpiration());
        MacAlgorithm alg = Jwts.SIG.HS512;

        Set<String> roles = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet());

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expiryDate)
                .claim("roles", roles)
                .issuer("com.smartsense")
                .signWith(securityConstants.getSigningKey(), alg).compact();

        return token;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().verifyWith(securityConstants.getSigningKey()).build().parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(securityConstants.getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new InvalidDataException("Invalid JWT token");
        }
    }

}
