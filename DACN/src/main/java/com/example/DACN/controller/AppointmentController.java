package com.example.DACN.controller;


import com.example.DACN.dto.ApiResponse;
import com.example.DACN.model.Appointment;
import com.example.DACN.model.status.AppointmentStatus;
import com.example.DACN.repository.AppointmentRepo;
import com.example.DACN.service.impl.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.Map;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {


    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAll(){
            return ResponseEntity.ok(appointmentService.getAllAppointment());
    }

    @GetMapping("/get/{Id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long Id){
        return  ResponseEntity.ok(appointmentService.getById(Id));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveAppointment(@RequestParam String username, @RequestParam Long eventId){
        return ResponseEntity.ok(appointmentService.saveAppointment(username,eventId));
    }

    @GetMapping("/by-user")
    public ResponseEntity<ApiResponse> getUserAppointment(@RequestParam String username){
        return ResponseEntity.ok(appointmentService.getUserAppointments(username));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ApiResponse> updateAppointmentStatus(@PathVariable Long id, @RequestBody Map<String, String> statusRequest){
        String status = statusRequest.get("status");
        AppointmentStatus newStatus = AppointmentStatus.valueOf(status.toUpperCase());

        return ResponseEntity.ok(appointmentService.updateAppointmentStatus(id,newStatus));
    }
}
