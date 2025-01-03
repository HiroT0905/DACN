package com.example.DACN.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.joda.time.DateTime;

import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BloodInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bloodType;
    private int quantity;
    private LocalDateTime lastUpdated; // thời gian cập nhật
    private LocalDateTime expirationDate; // ngày hết hạn máu

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Appointment appointment;
}
