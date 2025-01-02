package com.example.DACN.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Data
@Getter
@Setter
public class BloodInventoryDTO {
    private Long id;
    private String donationType;
    private int quantity;
    private LocalDateTime lastUpdated; // thời gian cập nhật
    private LocalDateTime expirationDate; // ngày hết hạn máu

    private AppointmentDTO appointmentDTO;

}
