package com.example.DACN.controller;


import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.EventDTO;
import com.example.DACN.model.Event;
import com.example.DACN.service.impl.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    //dung RequestBody vi nhan du lieu tu Json
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> addEvent(@RequestBody Map<String, String> eventData) {
        ApiResponse response = new ApiResponse();

        try {
            // Add the event
            response = eventService.addNewEvent(eventData);

            // Check if the response has a success status (200)
            if (response.getCode() == 200) {
                return ResponseEntity.ok(response); // Return a 200 OK with the response
            } else {
                // If the code is not 200, return a 4xx or 5xx status based on the response code
                return ResponseEntity.status(response.getCode()).body(response);
            }
        } catch (Exception e) {
            // Catch any general exceptions and return a 500 Internal Server Error
            response.setCode(500);
            response.setMessage("An error occurred while processing the request: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
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

    @GetMapping("/get-by-date-range")
    public ResponseEntity<ApiResponse> getEvents(
            @RequestParam(value = "startDate",required = false) String startDateString,
            @RequestParam(value = "endDate",required = false) String endDateString,
            @RequestParam(value = "unitId",required = false)String unitID
    ) {

        ApiResponse response = new ApiResponse();

            // Gọi service để xử lý logic
            if(!(startDateString.isEmpty()) && !(endDateString.isEmpty()) && (unitID != null)  ){
                LocalDate startDate = LocalDate.parse(startDateString);
                LocalDate endDate = LocalDate.parse(endDateString);
                long unitIDLong = Long.parseLong(unitID);
                response = eventService.getEventByDateAndUnitName(startDate, endDate,unitIDLong);
            }else if (!(startDateString.isEmpty()) && !(endDateString.isEmpty())){
                    LocalDate startDate = LocalDate.parse(startDateString);
                    LocalDate endDate = LocalDate.parse(endDateString);
                    response = eventService.getEventsByDateRange(startDate,endDate);
            }else if (unitID != null){
                long unitIDLong = Long.parseLong(unitID);

                response = eventService.getEventByUnit(unitIDLong);
            }else {
                response = eventService.getEventsByDateRange(LocalDate.now(),LocalDate.now().plusDays(30));
            }
            // Trả về phản hồi dựa trên mã code
            return ResponseEntity.ok(response);
    }




    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEvent(@PathVariable Long id, @ModelAttribute EventDTO event){
        return ResponseEntity.ok(eventService.updateEvent(id,event));
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<ApiResponse> deleteEvent(@PathVariable Long id){
        return ResponseEntity.ok(eventService.deleteEvent(id));
    }




}
