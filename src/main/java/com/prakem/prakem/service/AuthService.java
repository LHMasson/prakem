package com.prakem.prakem.service;

import com.prakem.prakem.dto.LoginRequest;
import com.prakem.prakem.dto.LoginResponse;
import com.prakem.prakem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthService {

    UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest loginRequest){
        return new LoginResponse();
    }
}
