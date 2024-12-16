package com.example.DACN.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
public class BloodDonationHistoryDTO {
    private Long id;
    private LocalDateTime donationDate;
    private Integer bloodAmount;
    private String donationLocation;
    private String notes;
    private String donationType;
    private String reactionAfterDonation;
    private LocalDateTime nextDonationDate;
    private Long appointmentId;
}
