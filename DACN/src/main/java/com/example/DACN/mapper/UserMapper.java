package com.example.DACN.mapper;

import com.example.DACN.dto.request.UserCreationRequest;
import com.example.DACN.dto.request.UserUpdateRequest;
import com.example.DACN.dto.response.UserResponse;
import com.example.DACN.dto.viewModel.UserInfoDTO;
import com.example.DACN.model.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapper;
import com.example.DACN.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
    
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    List<UserResponse> toUserResponse(List<User> all);

    UserInfo toUserInfo(UserInfoDTO userInfoDTO);

    UserInfoDTO toUserInfoDTO(UserInfo userInfo);
}
