package com.example.DACN.dto.viewModel;


import com.example.DACN.model.User;
import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserInfoDTO {
    private Long id;
    private String fullName;
    private LocalDate dob;
    private String sex;
    private String phone;
    private String address;
    private String email;

}
