package com.example.DACN.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Builder
@Data
public class EventDTO {
    private Long id;

    private String name;
    private LocalDate eventDate;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;

    private Long maxRegistrations;
    private Long currentRegistrations;
    private String status;

    private DonationUnitDTO donationUnitDTO;

    private List<AppointmentDTO>  appointments;
}
