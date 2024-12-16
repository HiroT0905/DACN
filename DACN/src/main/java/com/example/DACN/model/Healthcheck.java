package com.example.DACN.model;


import com.example.DACN.model.status.HealthCheckResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Healthcheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String healthMetrics;//Json dạng String

    private String notes;

    @OneToOne
    private Appointment appointment;


    @Enumerated(EnumType.STRING)
    private HealthCheckResult result;
}
