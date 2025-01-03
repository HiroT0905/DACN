package com.example.DACN.model;


import com.example.DACN.dto.HealthMetrics;
import com.example.DACN.model.status.HealthCheckResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

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


    public boolean isValidHealthCheck() throws IOException {
        // Chuyển đổi healthMetrics từ chuỗi JSON thành đối tượng

        ObjectMapper objectMapper = new ObjectMapper();
        HealthMetrics metrics;
        try {
            metrics = objectMapper.readValue(this.healthMetrics, HealthMetrics.class);
        } catch (IOException e) {
            this.result = HealthCheckResult.FAIL;
            return false;
        }
        boolean isValid = !metrics.hasChronicDiseases &&
                !metrics.hasRecentDiseases &&
                !metrics.hasSymptoms &&
                !metrics.isPregnantOrNursing &&
                metrics.HIVTestAgreement;

        // Logic kiểm tra điều kiện
        this.result = isValid ? HealthCheckResult.PASS : HealthCheckResult.FAIL;
        return isValid;
    }
}
