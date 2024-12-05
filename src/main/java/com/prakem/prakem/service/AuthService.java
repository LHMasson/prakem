package com.prakem.prakem.service;

import com.prakem.prakem.entity.User;
import com.prakem.prakem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean authenticate(String email, String password){
        User user = userRepository.findByEmail(email);
        if (user == null) {
           return false;
        }

        return user.verifyPassword(password);
    }
}
