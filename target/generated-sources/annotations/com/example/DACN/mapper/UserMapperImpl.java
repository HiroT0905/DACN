package com.example.DACN.mapper;

import com.example.DACN.dto.request.UserCreationRequest;
import com.example.DACN.dto.request.UserUpdateRequest;
import com.example.DACN.dto.response.UserResponse;
import com.example.DACN.dto.viewModel.UserInfoDTO;
import com.example.DACN.model.User;
import com.example.DACN.model.UserInfo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-17T14:22:19+0700",
    comments = "version: 1.6.2, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( request.getUsername() );
        user.password( request.getPassword() );
        user.userInfo( toUserInfo( request.getUserInfo() ) );

        return user.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.username( user.getUsername() );
        userResponse.userInfo( toUserInfoDTO( user.getUserInfo() ) );

        return userResponse.build();
    }

    @Override
    public void updateUser(User user, UserUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        user.setPassword( request.getPassword() );
        user.setUserInfo( toUserInfo( request.getUserInfo() ) );
    }

    @Override
    public List<UserResponse> toUserResponse(List<User> all) {
        if ( all == null ) {
            return null;
        }

        List<UserResponse> list = new ArrayList<UserResponse>( all.size() );
        for ( User user : all ) {
            list.add( toUserResponse( user ) );
        }

        return list;
    }

    @Override
    public UserInfo toUserInfo(UserInfoDTO userInfoDTO) {
        if ( userInfoDTO == null ) {
            return null;
        }

        UserInfo.UserInfoBuilder userInfo = UserInfo.builder();

        userInfo.id( userInfoDTO.getId() );
        userInfo.fullName( userInfoDTO.getFullName() );
        userInfo.dob( userInfoDTO.getDob() );
        userInfo.sex( userInfoDTO.getSex() );
        userInfo.phone( userInfoDTO.getPhone() );
        userInfo.address( userInfoDTO.getAddress() );
        userInfo.email( userInfoDTO.getEmail() );

        return userInfo.build();
    }

    @Override
    public UserInfoDTO toUserInfoDTO(UserInfo userInfo) {
        if ( userInfo == null ) {
            return null;
        }

        UserInfoDTO.UserInfoDTOBuilder userInfoDTO = UserInfoDTO.builder();

        userInfoDTO.id( userInfo.getId() );
        userInfoDTO.fullName( userInfo.getFullName() );
        userInfoDTO.dob( userInfo.getDob() );
        userInfoDTO.sex( userInfo.getSex() );
        userInfoDTO.phone( userInfo.getPhone() );
        userInfoDTO.address( userInfo.getAddress() );
        userInfoDTO.email( userInfo.getEmail() );

        return userInfoDTO.build();
    }
}
