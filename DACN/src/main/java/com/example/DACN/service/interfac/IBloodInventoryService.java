package com.example.DACN.service.interfac;


import com.example.DACN.dto.ApiResponse;
import org.joda.time.DateTime;

import java.time.LocalDateTime;

public interface IBloodInventoryService {
    ApiResponse addNew(String bloodType, int quantity, LocalDateTime lastUpdated, LocalDateTime expirationDate, Long appointmentId);
    ApiResponse getAll();
    ApiResponse get(Long Id);
    ApiResponse update(Long id,String bloodType, int quantity, LocalDateTime lastUpdated, LocalDateTime expirationDate, Long appointmentId);
}
