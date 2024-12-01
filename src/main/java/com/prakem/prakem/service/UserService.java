package com.prakem.prakem.service;

import com.prakem.prakem.dto.UserDTO;
import com.prakem.prakem.entity.User;
import com.prakem.prakem.exception.EmailAlreadyExistsException;
import com.prakem.prakem.mapper.UserMapper;
import com.prakem.prakem.repository.UserRepository;
import com.prakem.prakem.util.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {

        String email = userDTO.getEmail();

        PasswordValidator.validate(userDTO.getPassword());

        if (userExists(email)) {
            throw new EmailAlreadyExistsException("The email " + email + " is already in use");
        }
        User newUser = UserMapper.toEntity(userDTO);
        User savedUser = userRepository.save(newUser);
        return UserMapper.toDTO(savedUser);
    }

    public boolean userExists(String email) {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(email));
        return existingUser.isPresent();
    }
}
