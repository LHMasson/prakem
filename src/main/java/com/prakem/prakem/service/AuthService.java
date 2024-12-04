package com.prakem.prakem.service;

import com.prakem.prakem.dto.LoginRequest;
import com.prakem.prakem.dto.LoginResponse;
import com.prakem.prakem.entity.User;
import com.prakem.prakem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthService {

    UserRepository userRepository;
    JwtService jwtService;

    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
           return new LoginResponse(); // configurar falso
        }

        if (!user.verifyPassword(loginRequest.getPassword())) {
            return new LoginResponse(); // configurar falso
        }

        //gerar jwt

        return new LoginResponse(); // configurar true
    }
}
