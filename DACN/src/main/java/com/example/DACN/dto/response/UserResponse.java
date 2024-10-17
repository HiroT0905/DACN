package com.example.DACN.dto.response;

import com.example.DACN.dto.viewModel.UserDTO;
import com.example.DACN.dto.viewModel.UserInfoDTO;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserResponse {
    private String username;
    private UserInfoDTO userInfo;
}
