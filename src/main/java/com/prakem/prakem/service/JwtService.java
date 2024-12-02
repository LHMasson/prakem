package com.prakem.prakem.service;

import com.prakem.prakem.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshExpirationTime;

    @Autowired
    public JwtService(JwtConfig jwtConfig) {
        this.secretKey = jwtConfig.getSecretKey();
        this.accessTokenExpiration = jwtConfig.getAccessTokenExpiration();
        this.refreshExpirationTime = jwtConfig.getRefreshTokenExpiration();
    }

    /**
     * Generate a JWT token.
     *
     * @param claims   Custom claims to be added to the token.
     * @param subject  The subject (typically the username or user ID).
     * @param duration The expiration time in milliseconds.
     * @return A signed JWT token.
     */
    private String generateToken(Map<String, Object> claims, String subject, long duration) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + duration))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Generate an access token.
     *
     * @param subject The subject (typically the username or user ID).
     * @return A signed access token.
     */
    public String generateAccessToken(String subject) {
        return generateToken(new HashMap<>(), subject, accessTokenExpiration);
    }

    /**
     * Generate a refresh token.
     *
     * @param subject The subject (typically the username or user ID).
     * @return A signed refresh token.
     */
    public String generateRefreshToken(String subject) {
        return generateToken(new HashMap<>(), subject, refreshExpirationTime);
    }

    /**
     * Validate a JWT token.
     *
     * @param token   The token to validate.
     * @param subject The expected subject.
     * @return True if the token is valid, otherwise false.
     */
    public boolean isTokenValid(String token, String subject) {
        return subject.equals(getSubject(token)) && !isTokenExpired(token);
    }

    /**
     * Check if a token has expired.
     *
     * @param token The token to check.
     * @return True if the token has expired, otherwise false.
     */
    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    /**
     * Extract the expiration date from a token.
     *
     * @param token The token to extract from.
     * @return The expiration date.
     */
    private Date getExpirationDate(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Extract the subject from a token.
     *
     * @param token The token to extract from.
     * @return The subject.
     */
    public String getSubject(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Extract a specific claim from a token.
     *
     * @param token         The token to extract from.
     * @param claimsResolver A function to process the claims.
     * @param <T>           The type of the claim.
     * @return The extracted claim.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from a token.
     *
     * @param token The token to extract from.
     * @return The claims.
     */
    private Claims getAllClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}
