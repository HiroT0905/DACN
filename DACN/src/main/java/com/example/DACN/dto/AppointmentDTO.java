package com.example.DACN.dto;


import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@Builder

public class AppointmentDTO {
    private Long id ;
    private LocalDateTime appointmentDateTime;
    private Integer bloodAmount;
    private String status;

    private Long bloodDonationHistoryId;
    private Long healthCheckId;
    private String userId;
    private Long eventId;

}
