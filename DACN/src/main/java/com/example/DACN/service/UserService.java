package com.example.DACN.service;

import com.example.DACN.dto.request.ApiResponse;
import com.example.DACN.dto.request.UserCreationRequest;
import com.example.DACN.dto.request.UserUpdateRequest;
import com.example.DACN.dto.response.UserResponse;
import com.example.DACN.dto.viewModel.UserInfoDTO;
import com.example.DACN.exception.AppException;
import com.example.DACN.exception.ErrorCode;
import com.example.DACN.exception.UserNotFoundException;
import com.example.DACN.mapper.UserInfoMapper;
import com.example.DACN.mapper.UserMapper;
import com.example.DACN.model.User;
import com.example.DACN.model.UserInfo;
import com.example.DACN.repository.UserInfoRepo;
import com.example.DACN.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor()
public class UserService {
    @Autowired
    UserRepo userRepository;
    @Autowired
    UserInfoRepo userInfoRepository;
    @Autowired
    private UserInfoRepo userInfoRepo;
//

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public UserResponse getUserById(String userName) {
        return userMapper.toUserResponse(userRepository.findByUsername(userName));
    }

        public UserResponse createUser(UserCreationRequest request){

            User user = userMapper.toUser(request);
//            UserInfo userInfo = userInfoMapper.toUserInfo(request.);
            UserInfo usi = user.getUserInfo();
//            user.setPassword(passwordEncoder.encode(request.getPassword()));
            usi.setUser(user);
            user.setUserInfo(usi);
            try {
                // Lưu `User` và tự động lưu `UserInfo` do cascade
                User savedUser = userRepository.save(user);
                return userMapper.toUserResponse(savedUser);
            } catch (DataIntegrityViolationException exception) {
                throw new AppException(ErrorCode.USER_EXISTED);
            }
//            return userMapper.toUserResponse(saveduser);
        }

    public UserResponse updateUser(String userName, UserUpdateRequest request){

        if (request.getUserInfo() == null) {
            throw new IllegalArgumentException("UserInfo cannot be null");
        }
            User user = userRepository.findByUsername(userName);
            UserInfo usi = user.getUserInfo();
            usi.setUser(user);

    //            UserInfo uin4 = userMapper.toUserInfo(request.getUserInfo());
    //            user.setUserInfo(uin4);
    //            uin4.setUser(user);
            userMapper.updateUser(user, request);

            return userMapper.toUserResponse(userRepository.save(user));

    }

    public void DeleteUser(String id){
        userRepository.deleteById(id);
    }

}
