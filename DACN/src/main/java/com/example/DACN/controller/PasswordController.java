package com.example.DACN.controller;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.PasswordResetDTO;
import com.example.DACN.service.impl.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("")
public class PasswordController {
    @Autowired
    private UserManagementService userManagementService;

    @PostMapping("/auth/reset-password-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestBody Map<String, String> request) {
        try{


        String email = request.get("email");
        boolean emailSent = userManagementService.sendResetPasswordEmail(email);

        if (emailSent) {
            return ResponseEntity.ok("Password reset email sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found.");
        }
        }catch(Exception e){
            System.err.println("Error sending email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // API cập nhật mật khẩu
    @PutMapping("/auth/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        passwordResetDTO.setToken(passwordResetDTO.getToken());
        passwordResetDTO.setNewPassword(passwordResetDTO.getNewPassword());

         return ResponseEntity.ok(userManagementService.resetPassword(passwordResetDTO.getToken(),passwordResetDTO.getNewPassword()));


    }
}
