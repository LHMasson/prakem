package com.prakem.prakem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "O email não pode estar vazio")
    @Size(max = 254, message = "O email deve ter no máximo 254 caracteres")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "A senha não pode estar vazia")
    @Size(min = 8, max = 128, message = "A senha deve ter entre 8 e 128 caracteres")
    private String password;

    @NotBlank(message = "O nome completo não pode estar vazio")
    @Size(max = 100, message = "O nome completo deve ter no máximo 100 caracteres")
    private String fullname;

    private Byte[] photo;

    private Boolean enabled = true;
}
