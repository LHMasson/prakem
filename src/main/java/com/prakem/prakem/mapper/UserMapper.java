package com.prakem.prakem.mapper;

import com.prakem.prakem.dto.UserDTO;
import com.prakem.prakem.entity.User;

import java.io.IOException;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        return new User(
            dto.getEmail(),
            dto.getPassword(),
            dto.getFullname(),
            dto.getPhoto(),
            dto.getEnabled()
        );
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(
            user.getEmail(),
            user.getPassword(),
            user.getFullname(),
            user.getPhoto(),
            user.getEnabled()
        );
    }
}
