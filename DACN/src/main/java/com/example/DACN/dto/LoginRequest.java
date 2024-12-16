package com.example.DACN.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "CCCD is required")
    private String cccd;
    @NotBlank(message = "Password is required")
    private String password;
}
