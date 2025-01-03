package com.example.DACN.service.interfac;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.EventDTO;
import com.example.DACN.model.Event;
import com.example.DACN.model.status.AppointmentStatus;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Map;

public interface IEventService {
    ApiResponse addNewEvent(Map<String, String> event);
    ApiResponse deleteEvent(Long id);
    ApiResponse updateEvent(Long id, EventDTO event);
    ApiResponse getAllEvents();
    ApiResponse getEvent(Long id);
    ApiResponse getEventByDate(LocalDate eventDate);

    ApiResponse getEventByUnit(Long unitId);
    ApiResponse getEventsByDateRange(LocalDate startTime, LocalDate endTime);
    ApiResponse getEventByDateAndUnitName(LocalDate startDate,LocalDate endDate, Long unitName);
}
