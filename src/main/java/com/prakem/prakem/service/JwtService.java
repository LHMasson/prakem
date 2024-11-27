package com.prakem.prakem.service;

import com.prakem.prakem.config.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long AccessTokenExpiration;
    private final long refreshExpirationTime;

    @Autowired
    public JwtService(JwtConfig jwtConfig){
        this.secretKey = jwtConfig.getSecretKey();
        this.AccessTokenExpiration = jwtConfig.getAccessTokenExpiration();
        this.refreshExpirationTime = jwtConfig.getRefreshTokenExpiration();
    }


}
