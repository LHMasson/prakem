package com.prakem.prakem.controller;

import com.prakem.prakem.dto.LoginRequest;
import com.prakem.prakem.dto.LoginResponse;
import com.prakem.prakem.service.AuthService;
import com.prakem.prakem.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService){
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        boolean isAuthenticated =  authService.authenticate(request.getEmail(), request.getPassword());

        if (isAuthenticated) {
            String accessToken = jwtService.generateAccessToken(request.getEmail());
            String refreshToken = jwtService.generateRefreshToken(request.getEmail());
            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
