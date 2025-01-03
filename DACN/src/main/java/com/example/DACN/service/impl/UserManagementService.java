package com.example.DACN.service.impl;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.LoginRequest;
import com.example.DACN.dto.UserDTO;
import com.example.DACN.exception.OurException;
import com.example.DACN.model.PasswordResetToken;
import com.example.DACN.model.Role;
import com.example.DACN.model.User;
import com.example.DACN.model.UserInfo;
import com.example.DACN.repository.PasswordResetTokenRepo;
import com.example.DACN.repository.RoleRepo;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class UserManagementService {
    @Autowired
    private RoleRepo roleRepo;
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
    @Autowired
            private PasswordResetTokenRepo passwordResetTokenRepo;

    Role userrole = new Role(2L);

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse register(Map<String, String> userData) {
        ApiResponse resp = new ApiResponse();
        try {
            // Lấy thông tin từ Map
            String username = userData.get("cccd");
            String password = userData.get("password");
            String phone = userData.get("phone");
            String email = userData.get("email");
            String fullName = userData.get("fullName");
            String dobStr = userData.get("dob");
            String sex = userData.get("sex");
            String address = userData.get("address");// Role name để tìm Role từ DB

            // Kiểm tra các trường không được bỏ trống
            if (username == null || password == null || fullName == null || dobStr == null || sex == null || address == null) {
                throw new IllegalArgumentException("Missing required fields.");
            }

            // Chuyển đổi ngày sinh từ String
            LocalDate dob = LocalDate.parse(dobStr);

            // Tạo đối tượng UserInfo
            UserInfo userInfo = UserInfo.builder()
                    .fullName(fullName)
                    .dob(dob)
                    .sex(sex)
                    .address(address)
                    .build();
            userInfo = userInfoRepo.save(userInfo);
            // Lấy Role từ DB
            Role role = userrole; // Giả sử bạn có method findByName

            if (role == null) {
                throw new OurException("Role not found.");
            }

            // Tạo đối tượng User
            User user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password)) // Mã hóa mật khẩu
                    .phone(phone)
                    .email(email)
                    .userInfo(userInfo)
                    .role(role)
                    .build();

            // Kiểm tra người dùng đã tồn tại
            if (usersRepo.existsByUsername(username)) {
                throw new OurException(username + " already exists.");
            }

            // Lưu thông tin người dùng
            User savedUser = usersRepo.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            resp.setCode(200);
            resp.setMessage("User and UserInfo saved successfully.");
            resp.setUser(userDTO);
        } catch (OurException e) {
            resp.setCode(409);
            resp.setError(e.getMessage());
        } catch (IllegalArgumentException e) {
            resp.setCode(400);
            resp.setError(e.getMessage());
        } catch (Exception e) {
            resp.setCode(500);
            resp.setError("Internal Server error: " + e.getMessage());
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
                var dto = Utils.mapUserEntityToUserDTO(user);

                response.setCode(200);
                response.setRole(user.getRole().getName());
                response.setToken(jwt);
                response.setUser(dto);
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

    public ApiResponse updateUser(String cccd, Map<String, String> userData) {
        ApiResponse reqRes = new ApiResponse();
        try {
            // Tìm user hiện tại từ database
            User existingUser = usersRepo.findUserByUsername(cccd)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Cập nhật thông tin cơ bản
            updateBasicUserInfo(existingUser, userData);

            // Cập nhật UserInfo nếu có
            updateUserInfo(existingUser, userData);

            // Cập nhật Role nếu có
            updateRole(existingUser, userData);

            // Lưu các thay đổi
            User savedUser = usersRepo.save(existingUser);

            // Tạo UserDTO để trả về
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            // Đóng gói phản hồi
            reqRes.setCode(200);
            reqRes.setMessage("User updated successfully");
            reqRes.setUser(userDTO);

        } catch (RuntimeException e) {
            reqRes.setCode(404);
            reqRes.setMessage(e.getMessage());
        } catch (Exception e) {
            reqRes.setCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }

    private void updateBasicUserInfo(User existingUser, Map<String, String> userData) {
        if (userData.containsKey("phone")) {
            existingUser.setPhone(userData.get("phone"));
        }
        if (userData.containsKey("email")) {
            existingUser.setEmail(userData.get("email"));
        }
        if (userData.containsKey("password")) {
            existingUser.setPassword(userData.get("password"));
        }
    }

    private void updateUserInfo(User existingUser, Map<String, String> userData) {
        if (userData.containsKey("fullName") || userData.containsKey("dob")
                || userData.containsKey("sex") || userData.containsKey("address")) {
            UserInfo userInfo = existingUser.getUserInfo();
            if (userInfo == null) {
                userInfo = new UserInfo();
                existingUser.setUserInfo(userInfo);
            }
            if (userData.containsKey("fullName")) {
                userInfo.setFullName(userData.get("fullName"));
            }
            if (userData.containsKey("dob")) {
                userInfo.setDob(LocalDate.parse(userData.get("dob"))); // Đảm bảo định dạng đúng
            }
            if (userData.containsKey("sex")) {
                userInfo.setSex(userData.get("sex"));
            }
            if (userData.containsKey("address")) {
                userInfo.setAddress(userData.get("address"));
            }
        }
    }

    private void updateRole(User existingUser, Map<String, String> userData) {
        if (userData.containsKey("roleId")) {
            Role role = roleRepo.findById(Long.parseLong(userData.get("roleId")))
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            existingUser.setRole(role);
        }
    }
    public ApiResponse updateUser(String cccd, User updatedUser) {
    ApiResponse reqRes = new ApiResponse();
    try {
        User existingUser = usersRepo.findUserByUsername(cccd).orElseThrow(() -> new RuntimeException("User Not found"));

        // Cập nhật thông tin cơ bản (cccd, pass, phone, email)
        updateBasicUserInfo(existingUser, updatedUser);

        // Cập nhật UserInfo nếu có
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

    } catch (Exception e) {
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


        public ApiResponse resetPassword(String token, String newPassword) {
        // Tìm kiếm token trong cơ sở dữ liệu
        ApiResponse response = new ApiResponse();
            PasswordResetToken resetToken = passwordResetTokenRepo.findByToken(token);

            try{
                if (resetToken != null) {
                    User user = resetToken.getUser();  // Lấy người dùng từ token
                    // Kiểm tra xem token chưa hết hạn
                    if (resetToken.getExpiryDate().isAfter(LocalDateTime.now())) {

                        // Mã hóa mật khẩu mới
                        user.setPassword(passwordEncoder.encode(newPassword));

                        // Lưu lại người dùng với mật khẩu mới
                        usersRepo.save(user);

                        // Xóa token sau khi sử dụng
                        passwordResetTokenRepo.delete(resetToken);

                        response.setCode(200);
                        response.setMessage("Password reset successfully");
                        return response;
                    }
                }
            }catch(OurException e){
                response.setCode(404);
                response.setMessage(e.getMessage());
                return response;
            }catch(Exception e){
                response.setCode(500);
                response.setMessage("Error resetting password " + e.getMessage());
                return response;
            }
        return response;
    }


    private boolean validateToken(User user, String token) {
        PasswordResetToken resetToken = passwordResetTokenRepo.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }


    public boolean sendResetPasswordEmail(String email) {
        Optional<User> userOpt = usersRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = UUID.randomUUID().toString();

            //Kiem tra
            PasswordResetToken existingToken = passwordResetTokenRepo.findByUser_Username(user.getUsername());
            if (existingToken != null) {
                // Nếu có token cũ, xóa token cũ
                passwordResetTokenRepo.delete(existingToken);
            }
            // Lưu token vào cơ sở dữ liệu hoặc cache (ví dụ: Redis)
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
            passwordResetTokenRepo.save(resetToken);

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

