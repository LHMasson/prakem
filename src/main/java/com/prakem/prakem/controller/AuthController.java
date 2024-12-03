package com.prakem.prakem.controller;

import com.prakem.prakem.dto.LoginRequest;
import com.prakem.prakem.dto.LoginResponse;
import com.prakem.prakem.service.AuthService;
import com.prakem.prakem.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
