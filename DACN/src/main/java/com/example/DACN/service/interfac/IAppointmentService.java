package com.example.DACN.service.interfac;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.HealthMetrics;
import com.example.DACN.model.Appointment;
import com.example.DACN.model.status.AppointmentStatus;

public interface IAppointmentService {
    ApiResponse saveAppointment(String username, Long eventId, HealthMetrics healthMetrics);

    ApiResponse getAllAppointment();

    ApiResponse getById(Long Id);

    ApiResponse getUserAppointments(String username);
    ApiResponse updateAppointmentStatus(Long id, AppointmentStatus status);

    ApiResponse getAppointmentPendingUser(String username);

    ApiResponse deleteAppointment(Long id);
}
