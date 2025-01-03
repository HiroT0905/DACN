package com.example.DACN.controller;
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
        return ResponseEntity.ok(response);
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
