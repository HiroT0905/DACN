package com.example.DACN.service.impl;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.LoginRequest;
import com.example.DACN.dto.UserDTO;
import com.example.DACN.exception.OurException;
import com.example.DACN.exception.UserValidator;
import com.example.DACN.model.Role;
import com.example.DACN.model.User;
import com.example.DACN.model.UserInfo;
import com.example.DACN.repository.UserInfoRepo;
import com.example.DACN.repository.UsersRepo;
import com.example.DACN.service.utils.JWTUtils;
import com.example.DACN.service.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserManagementService {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserInfoRepo userInfoRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    Role userrole = new Role(2L);

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse register(User user ){
        ApiResponse resp = new ApiResponse();
//        var existedUser = usersRepo.findUserByUsername(registrationRequest.getCccd());

            try{
                UserValidator.validateUserInput(user);
                if (user.getRole() == null || user.getRole().getUsers().isEmpty()){
                    user.setRole(userrole);
                }
                if (usersRepo.existsByUsername(user.getUsername())){
                    throw new OurException(user.getUsername() + " " + "Already Exists");
                }
                UserInfo savedUserInfo =  userInfoRepo.save(user.getUserInfo());
//                UserInfoDTO userInfoDTO = Utils.mapUserEntityToUserInfoDTO(savedUserInfo);
                user.setUsername(user.getUsername());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setUserInfo(savedUserInfo);
                try{
                    User savedUser = usersRepo.save(user);
                    UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
                    resp
                            .setCode(200);
                    resp        .setMessage("User, UserInfo, and UserContact saved successfully");
                    resp      .setUser(userDTO);

                }catch(Exception e){
                    e.printStackTrace();
                }


                //save userinfo


            }catch(OurException e){
                resp.setCode(409);resp.setError(e.getMessage());
            }catch(Exception e){
                resp.setCode(500);resp.setError("Internal Server error: " +e.getMessage());
            }


        return resp;
    }

    public  ApiResponse login (LoginRequest loginRequest){
        ApiResponse response = new ApiResponse();
        try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getCccd(), loginRequest.getPassword()));
                var user = usersRepo.findUserByUsername(loginRequest.getCccd()).orElseThrow(()-> new OurException("user not found")) ;
//                var userInfo = userInfoRepo.findUserInfoByUserUsername(loginRequest.getCccd()).orElseThrow();
                var jwt = jwtUtils.generateToken(user);
                var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
                response.setCode(200);
                response.setRole(user.getRole().getName());
                response.setToken(jwt);
//                response.setUserInfo(user.getUserInfo());
//                response.setUser(user);
//                response.setRefreshToken(refreshToken);
                response.setExpirationTime("24hours");
                response.setMessage("Successfully logged in");

        }
        catch(BadCredentialsException e){
            response.setCode(401);
            response.setMessage("Thông tin tài khoản hoặc mật khẩu không chính xác");
        }catch (UsernameNotFoundException e) {
            response.setCode(404); // Not Found
            response.setMessage("Người dùng không tồn tại");
        }
        catch(Exception e){
            response.setCode(500);
            response.setError(e.getMessage());
        }

        return response;
    }


    public ApiResponse refreshToken(ApiResponse refreshTokenReqiest){
        ApiResponse response = new ApiResponse();
            try{
            String cccd = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            User users = usersRepo.findUserByUsername(cccd).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setCode(200);
            return response;

        }catch (Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }


    public ApiResponse getAllUsers() {
        ApiResponse reqRes = new ApiResponse();

        try {
            List<User> result = usersRepo.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToDTO(result);
             if (!result.isEmpty()) {
                 reqRes.setCode(200);
                 reqRes.setMessage("Successful");
                reqRes.setUserList(userDTOList);
            } else {
                reqRes.setCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }



    public ApiResponse getUsersById(String id) {
        ApiResponse reqRes = new ApiResponse();
        try {
            User usersById = usersRepo.findUserByUsername(id).orElseThrow(() -> new RuntimeException("User Not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(usersById);
            reqRes.setCode(200);
            reqRes.setMessage("successful");
            reqRes.setUser(userDTO);
        }catch(OurException e){
            reqRes.setCode(404);
            reqRes.setMessage(e.getMessage());
        }
        catch (Exception e) {
            reqRes.setCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ApiResponse deleteUser(String userId) {
        ApiResponse reqRes = new ApiResponse();
        try {
            Optional<User> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                reqRes.setCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public ApiResponse updateUser(String cccd, User updatedUser) {
        ApiResponse reqRes = new ApiResponse();
        try {
            User existingUser = usersRepo.findUserByUsername(cccd).orElseThrow(() -> new RuntimeException("User Not found"));
            //Cập nhật thông tin cơ bản (cccd, pass, phone, email)
            updateBasicUserInfo(existingUser, updatedUser);

            //Cập nhật UserInfo nếu có
            if (updatedUser.getUserInfo() != null) {
                UserInfo updatedUserInfo = updateUserInfo(existingUser.getUserInfo(), updatedUser.getUserInfo());
                existingUser.setUserInfo(updatedUserInfo);
            }
                // Cập nhật role (nếu cần)
            if (updatedUser.getRole() != null) {
                existingUser.setRole(updatedUser.getRole());
            }

                // Lưu thay đổi
                User savedUser = usersRepo.save(existingUser);
                UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

                // Đóng gói phản hồi
                reqRes.setCode(200);
                reqRes.setMessage("User updated successfully");
                reqRes.setUser(userDTO);



        }catch(OurException e){
            reqRes.setCode(404);
            reqRes.setMessage("User not found for update");
        }
        catch (Exception e) {
            reqRes.setCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }


        public ApiResponse getMyInfo(String cccd){
            ApiResponse response = new ApiResponse();
            try {
                User user = usersRepo.findUserByUsername(cccd).orElseThrow(() -> new OurException("User Not Found"));
                UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
                response.setCode(200);
                response.setMessage("successful");
                response.setUser(userDTO);

            }catch (OurException e){
                response.setCode(404);
                response.setMessage( e.getMessage());
            }catch (Exception e){
                response.setCode(500);
                response.setMessage("Error getting all users " + e.getMessage());
            }
            return response;

        }

    private void updateBasicUserInfo(User existingUser, User updatedUser) {
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        if (updatedUser.getPhone() != null) {
            existingUser.setPhone(updatedUser.getPhone());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
    }

    private UserInfo updateUserInfo(UserInfo existingUserInfo, UserInfo updatedUserInfo) {
        if (updatedUserInfo.getFullName() != null) {
            existingUserInfo.setFullName(updatedUserInfo.getFullName());
        }
        if (updatedUserInfo.getDob() != null) {
            existingUserInfo.setDob(updatedUserInfo.getDob());
        }
        if (updatedUserInfo.getSex() != null) {
            existingUserInfo.setSex(updatedUserInfo.getSex());
        }
        if (updatedUserInfo.getAddress() != null) {
            existingUserInfo.setAddress(updatedUserInfo.getAddress());
        }
        return userInfoRepo.save(existingUserInfo);
    }


    public boolean resetPassword(String email, String newPassword, String token) {
        // Kiểm tra token hợp lệ và chưa hết hạn
        // Nếu hợp lệ, mã hóa mật khẩu mới và lưu lại
        Optional<User> userOpt = usersRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Kiểm tra token và thời gian hết hạn
            boolean isTokenValid = validateToken(user, token);
            if (isTokenValid) {
                user.setPassword(passwordEncoder.encode(newPassword));
                usersRepo.save(user);
                return true;
            }
        }
        return false;
    }
    private boolean validateToken(User user, String token) {
        // Kiểm tra mã token hợp lệ và chưa hết hạn
        // Thực hiện kiểm tra với cơ sở dữ liệu hoặc cache
        return true;
    }
    public boolean sendResetPasswordEmail(String email) {
        Optional<User> userOpt = usersRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = UUID.randomUUID().toString();

            // Lưu token vào cơ sở dữ liệu hoặc cache (ví dụ: Redis)
            // Bạn có thể tạo một bảng `PasswordResetTokens` hoặc sử dụng Redis để lưu trữ token và thời gian hết hạn

            String resetLink = "http://localhost:3000/reset-password?token=" + token;

            // Gửi email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("To reset your password, please click the link below:\n" + resetLink);
            mailSender.send(message);
            return true;
        }
        return false;
    }
}

