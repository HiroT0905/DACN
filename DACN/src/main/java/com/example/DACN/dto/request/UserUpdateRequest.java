package com.example.DACN.dto.request;

import com.example.DACN.dto.viewModel.UserInfoDTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {
    private String password;
    private UserInfoDTO userInfo;


}
