package com.example.DACN.service.impl;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.model.Appointment;
import com.example.DACN.model.Healthcheck;
import com.example.DACN.model.status.HealthCheckResult;
import com.example.DACN.repository.HealthCheckRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {
    @Autowired
    HealthCheckRepo healthcheckRepository;

    public void processHealthCheck(Long healthCheckId) {
        try {
            Healthcheck healthcheck = healthcheckRepository.findById(healthCheckId).orElseThrow(() -> new RuntimeException("Healthcheck not found"));
            boolean isValid = healthcheck.isValidHealthCheck();

            if (isValid) {
                // Đặt kết quả là PASS
                healthcheck.setResult(HealthCheckResult.PASS);
            } else {
                // Đặt kết quả là FAIL
                healthcheck.setResult(HealthCheckResult.FAIL);
            }

            // Lưu lại kết quả
            healthcheckRepository.save(healthcheck);

        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi nếu có
        }
    }

}
