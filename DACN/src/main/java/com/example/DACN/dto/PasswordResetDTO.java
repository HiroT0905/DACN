package com.example.DACN.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// PasswordResetDTO.java

@Setter
@Getter
public class PasswordResetDTO {
    // Getters and Setters
    private String newPassword;
    private String token;

}
