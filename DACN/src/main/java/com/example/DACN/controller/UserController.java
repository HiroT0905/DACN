package com.example.DACN.controller;//package com.example.DACN.controller;
//
//
//import com.example.DACN.dto.request.ApiResponse;
//import com.example.DACN.dto.request.UserCreationRequest;
//import com.example.DACN.dto.request.UserUpdateRequest;
//import com.example.DACN.dto.response.UserResponse;
//
//import com.example.DACN.exception.UserNotFoundException;
////import com.example.DACN.service.UserService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//
//@RequestMapping("/users/")
//public class UserController {
//    @Autowired
//    private UserService userService;
////    @PostMapping
////    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
////        return ApiResponse.<UserResponse>builder().data(userService.createUser(request))
////                .build();
////    }
////    @PostMapping
////    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
////    return ApiResponse.<UserResponse>builder()
////            .result(userService.createUser(request))
////            .build();
////}
//
//
//    @GetMapping("")
//    ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
//        List<UserResponse> users = userService.getAllUsers();
//        ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
//                .result(users)
//                .message("Successfully retrieved all users")
//                .code(200)
//                .build();
//        return ResponseEntity.ok(response);
//    }
////
////
//    @GetMapping("/{userName}")
//    ApiResponse<UserResponse> getUserById(@PathVariable String userName) {
//        return ApiResponse.<UserResponse>builder()
//                .result(userService.getUserById(userName))
//                .build();
//    }
////
////    @PutMapping("/{userName}")
////    ApiResponse<UserResponse> updateUser(@PathVariable String userName, @RequestBody UserUpdateRequest request) {
////        return ApiResponse.<UserResponse>builder().result(userService.updateUser(userName, request)).build();
////    }
////@PutMapping("/{id}")
////public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable long id, @RequestBody UserUpdateRequest request) {
////    try {
////        UserResponse updatedUser = userService.updateUser(id, request);
////        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
////                .result(updatedUser)
////                .message("Successfully updated user")
////                .code(200)
////                .build();
////        return ResponseEntity.ok(response);
////    } catch (UserNotFoundException e) {
////        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
////                .message(e.getMessage())
////                .code(404)
////                .build();
////        return ResponseEntity.status(404).body(response);
////    } catch (Exception e) {
////        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
////                .message("An error occurred while updating the user")
////                .code(500)
////                .build();
////        return ResponseEntity.status(500).body(response);
////    }
////}
//
////    @DeleteMapping("/{id}")
////    String deleteUser(@PathVariable long id) {
////        userService.DeleteUser(id);
////        return "User has been deleted";
////    }
//}
//
//

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.LoginRequest;
import com.example.DACN.dto.PasswordResetDTO;
import com.example.DACN.model.User;
import com.example.DACN.repository.UsersRepo;
import com.example.DACN.service.impl.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserManagementService userManagementService;;
    @Autowired
    private UsersRepo usersRepo;

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        return  ResponseEntity.ok(userManagementService.register(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse> login (@RequestBody LoginRequest loginRequest){
        var response = userManagementService.login(loginRequest);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody ApiResponse req){
        return  ResponseEntity.ok(userManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ApiResponse> getAllUsers(){
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }
    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ApiResponse> getProfile(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cccd = auth.getName();
        ApiResponse response = userManagementService.getMyInfo(cccd);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/admin/get-users/{cccd}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable String cccd){
        return ResponseEntity.ok(userManagementService.getUsersById(cccd));
    }
    @PutMapping("/admin/update/{cccd}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String cccd, @RequestBody User req){
        return ResponseEntity.ok(userManagementService.updateUser(cccd, req));
    }

    @DeleteMapping("/admin/delete/{cccd}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String cccd){
        return ResponseEntity.ok(userManagementService.deleteUser(cccd));
    }
    //Doi mat khau


//    private JavaMailSender mailSender;
    @PostMapping("/reset-password-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean emailSent = userManagementService.sendResetPasswordEmail(email);

        if (emailSent) {
            return ResponseEntity.ok("Password reset email sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found.");
        }
    }

    // API cập nhật mật khẩu
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        boolean isUpdated = userManagementService.resetPassword(passwordResetDTO.getEmail(), passwordResetDTO.getNewPassword(), passwordResetDTO.getToken());

        if (isUpdated) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token or email.");
        }
    }
}
