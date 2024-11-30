package com.prakem.prakem.mapper;

import com.prakem.prakem.dto.UserDTO;
import com.prakem.prakem.entity.User;
import com.prakem.prakem.enumerator.Role;

import java.util.stream.Collectors;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        return new User(
            dto.getEmail(),
            dto.getPassword(),
            dto.getFullname(),
            dto.getPhoto(),
            dto.getEnabled(),
            dto.getRoles()
        );
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(
            user.getEmail(),
            user.getPassword(),
            user.getFullname(),
            user.getPhoto(),
            user.getEnabled(),
            user.getRoles().stream().map(Role::name).collect(Collectors.toSet())
        );
    }
}
