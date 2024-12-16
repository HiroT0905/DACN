package com.example.DACN.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserInfoDTO {

    private Long id ;
    private String fullName ;
    private LocalDate dob;
    private String sex ;
    private String address ;
}
