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
import com.example.DACN.model.Role;
import com.example.DACN.model.User;
import com.example.DACN.model.UserInfo;
import com.example.DACN.repository.UsersRepo;
import com.example.DACN.service.impl.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserManagementService userManagementService;;
    @Autowired
    private UsersRepo usersRepo;




    @PostMapping(value = "/auth/register", consumes = "application/json")
    public ResponseEntity<ApiResponse> registerUser(
            @RequestBody Map<String, String> userData) {

        ApiResponse response = userManagementService.register(userData);

        return ResponseEntity.status(response.getCode()).body(response);
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

//    @PostMapping(value = "/admin/update/{cccd}", consumes = {"application/json", "application/json;charset=UTF-8"})
//    public ResponseEntity<ApiResponse> updateUser(@PathVariable String cccd,
//                                                  @RequestBody User updatedUser) {
//        ApiResponse apiResponse = userManagementService.updateUser(cccd, updatedUser);
//        return ResponseEntity.ok(apiResponse);
//    }
//    @PutMapping(value = "/admin/update/{cccd}", consumes = {"application/json", "application/json;charset=UTF-8"})
//    public ResponseEntity<ApiResponse> updateUser(
//            @PathVariable String cccd,
//            @RequestBody User updatedUser) {
//        // Xử lý logic cập nhật
//        ApiResponse response = userManagementService.updateUser(cccd, updatedUser);
//        return ResponseEntity.ok(response);
//    }
    @PutMapping(value = "/admin/update/{cccd}", consumes = "application/json")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable String cccd,
            @RequestBody Map<String, String> userData) {
        ApiResponse response = null;
        try {
            response = userManagementService.updateUser(cccd, userData);
            return ResponseEntity.status(response.getCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/admin/delete/{cccd}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String cccd){
        return ResponseEntity.ok(userManagementService.deleteUser(cccd));
    }
    //Doi mat khau


//    private JavaMailSender mailSender;
}
