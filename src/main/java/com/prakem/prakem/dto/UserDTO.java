package com.prakem.prakem.dto;

import com.prakem.prakem.enumerator.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Full name cannot be null")
    @Size(max = 255, message = "Full name cannot exceed 255 characters")
    private String fullname;

    private byte[] photo;

    private Boolean enabled = true;

    private Set<String> roles;

    public Set<Role> getRoles() {
        if (roles == null || roles.isEmpty()) {
            return Set.of(Role.USER);
        }
        return roles.stream()
                .map(Role::fromString)
                .collect(Collectors.toSet());
    }
}
