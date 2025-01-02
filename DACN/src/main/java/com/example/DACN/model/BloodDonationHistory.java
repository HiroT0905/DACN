package com.example.DACN.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodDonationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime donationDateTime;  // Ngày hiến máu
    private Integer bloodAmount;         // Lượng máu đã hiến (mL)
    private String donationLocation;     // Địa điểm hiến máu
    private String notes;// Ghi chú bổ sung

    private String donationType;  // Loại hiến máu (toàn phần, tiểu cầu, huyết tương)
    private String reactionAfterDonation;  // Phản ứng sau khi hiến máu (Dizziness, Fatigue, v.v.)



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private Appointment appointment;  // Liên kết với cuộc hẹn


}