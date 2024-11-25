package com.prakem.prakem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    @Schema(description = "Email Address", example = "user@example.com", nullable = false)
    private String email;

    @Schema(description = "User Password with a minimum length of 8 characters", nullable = false)
    private String password;

    @Schema(description = "User's full name", nullable = false)
    private String fullname;

    @Schema(description = "User photo in byte array", type = "byte[]", format = "byte[]", nullable = true, example = "iVBORw0KGgoAAAANSUhEUgAAAAUA")
    private byte[] photo;

    @Schema(description = "Indicates whether the user is enabled", defaultValue = "true", nullable = true)
    private Boolean enabled = true;
}
