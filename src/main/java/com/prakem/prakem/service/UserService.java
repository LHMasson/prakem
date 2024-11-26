package com.prakem.prakem.service;

import com.prakem.prakem.dto.UserDTO;
import com.prakem.prakem.entity.User;
import com.prakem.prakem.mapper.UserMapper;
import com.prakem.prakem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.prakem.prakem.mapper.UserMapper.toEntity;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO user) {
        User newUser = UserMapper.toEntity(user);
        User savedUser = userRepository.save(newUser);
        return UserMapper.toDTO(savedUser);
    }
}
