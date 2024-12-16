package com.example.DACN.controller;


import com.example.DACN.dto.ApiResponse;
import com.example.DACN.model.Event;
import com.example.DACN.service.impl.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    //dung RequestBody vi nhan du lieu tu Json
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addEvent(@RequestBody Event event){
        return ResponseEntity.ok(eventService.addNewEvent(event));
    }
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAll(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/get/{Id}")
    public ResponseEntity<ApiResponse > getEvent(@PathVariable Long Id){
        return ResponseEntity.ok(eventService.getEvent(Id));
    }

    @GetMapping("/get-by-date")
    public ResponseEntity<ApiResponse> getByDate(@RequestParam LocalDate date){
        return ResponseEntity.ok(eventService.getEventByDate(date));
    }

    @GetMapping("/by-unit")
    public ResponseEntity<ApiResponse> getEventsByDonationUnit(@RequestParam  Long  unitId){
        return ResponseEntity.ok(eventService.getEventByUnit(unitId));
    }
    @GetMapping("/by-date-unit")
    public ResponseEntity<ApiResponse> getEventsByDateAndUnit(@RequestParam LocalDate date, @RequestParam Long unitId){
        return ResponseEntity.ok(eventService.getEventByDateAndUnitName(date,unitId));
    }




    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEvent(@PathVariable Long id, @RequestParam Event event){
        return ResponseEntity.ok(eventService.updateEvent(id,event));
    }

    @DeleteMapping("/delete")
    public  ResponseEntity<ApiResponse> deleteEvent(@RequestParam Long id){
        return ResponseEntity.ok(eventService.deleteEvent(id));
    }
}
