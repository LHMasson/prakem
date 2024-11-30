package com.prakem.prakem.dto;

import com.prakem.prakem.enumerator.Role;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Email Address", example = "user@example.com", nullable = false)
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @Schema(description = "User Password with a minimum length of 8 characters", nullable = false)
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Schema(description = "User's full name", nullable = false)
    @NotNull(message = "Full name cannot be null")
    @Size(max = 255, message = "Full name cannot exceed 255 characters")
    private String fullname;

    @Schema(description = "User photo in byte array", type = "byte[]", format = "byte[]", nullable = true, example = "iVBORw0KGgoAAAANSUhEUgAAAAUA")
    private byte[] photo;

    @Schema(description = "Indicates whether the user is enabled", defaultValue = "true", nullable = true)
    private Boolean enabled = true;

    @Schema(description = "Set of roles assigned to the user, e.g., ['USER', 'ADMIN']", example = "[\"USER\", \"ADMIN\"]", nullable = true, defaultValue = "[USER]")
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
