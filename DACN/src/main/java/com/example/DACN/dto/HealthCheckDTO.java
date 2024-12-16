package com.example.DACN.dto;

import com.example.DACN.model.status.HealthCheckResult;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthCheckDTO {
    private Long id;
    private String healthMetrics;
    private HealthCheckResult result;
    private String  notes;
    private Long appointmentId;
}
