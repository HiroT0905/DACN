package com.example.DACN.mapper;

import com.example.DACN.dto.request.UserCreationRequest;
import com.example.DACN.model.UserInfo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-17T12:59:51+0700",
    comments = "version: 1.6.2, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserInfoMapperImpl implements UserInfoMapper {

    @Override
    public UserInfo toUserInfo(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        UserInfo.UserInfoBuilder userInfo = UserInfo.builder();

        return userInfo.build();
    }
}
