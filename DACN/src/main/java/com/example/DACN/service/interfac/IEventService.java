package com.example.DACN.service.interfac;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.model.Event;
import com.example.DACN.model.status.AppointmentStatus;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public interface IEventService {
    ApiResponse addNewEvent(Event event);
    ApiResponse deleteEvent(Long id);
    ApiResponse updateEvent(Long id,Event event);
    ApiResponse getAllEvents();
    ApiResponse getEvent(Long id);
    ApiResponse getEventByDate(LocalDate eventDate);

    ApiResponse getEventByUnit(Long unitId);

    ApiResponse getEventByDateAndUnitName(LocalDate eventDate, Long unitName);
}
