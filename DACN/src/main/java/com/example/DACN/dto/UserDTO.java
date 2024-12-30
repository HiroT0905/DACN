package com.example.DACN.dto;

import com.example.DACN.model.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {

    private String username;
    private String phone ;
    private String email ;

    private UserInfoDTO userInfoDTO;
    private Role role ;

    private List<AppointmentDTO> appointments = new ArrayList<>();
    private Set<BloodInventoryDTO> bloodDonationHistories;
}
