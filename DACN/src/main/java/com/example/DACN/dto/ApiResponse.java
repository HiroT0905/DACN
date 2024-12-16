package com.example.DACN.dto;

import com.example.DACN.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    @Builder.Default
    private int code = 1000;
    private String error;
    private String message;

    private String token;
    String refreshToken;
    String expirationTime;

    String role ;
    LoginRequest loginRequest;
    UserDTO user;
    EventDTO eventDTO;
    List<EventDTO> eventDTOList;
    List<UserDTO> userList;

    DonationUnitDTO donationUnitDTO;
    List<DonationUnitDTO> donationUnitList;
    HealthCheckDTO healthCheckDTO;
    AppointmentDTO appointmentDTO;
    List<AppointmentDTO> appointmentDTOList;

}