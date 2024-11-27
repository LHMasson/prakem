package com.prakem.prakem.config;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {

    @Getter
    private final SecretKey secretKey;

    @Getter
    private final long accessTokenExpiration = 1000 * 60 * 15;

    @Getter
    private final long refreshTokenExpiration = 1000 * 60 * 60 * 24 * 7;

    public JwtConfig(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }
}
