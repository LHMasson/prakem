package com.prakem.prakem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    String accessToken;
    String refreshToken;
}
