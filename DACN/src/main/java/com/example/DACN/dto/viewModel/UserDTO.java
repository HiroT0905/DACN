package com.example.DACN.dto.viewModel;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String CCCd;
    private String username;
    private String password;
}
