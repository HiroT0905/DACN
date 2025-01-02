package com.example.DACN.controller;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.BloodInventoryDTO;
import com.example.DACN.service.impl.BloodInventoryService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/blood-inventory")
public class BloodInventoryController {

    @Autowired
    private BloodInventoryService bloodInventoryService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNew(@RequestBody BloodInventoryDTO dataRequest, @RequestParam Long appointmentId){
        String bloodType = dataRequest.getDonationType();
        int quantity = dataRequest.getQuantity();
        LocalDateTime lastUpdated = dataRequest.getLastUpdated();
        LocalDateTime expirationDate = dataRequest.getExpirationDate();

        return ResponseEntity.ok(bloodInventoryService.addNew(bloodType,quantity,lastUpdated,expirationDate,appointmentId));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse > getAll (){
        return ResponseEntity.ok(bloodInventoryService.getAll());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<ApiResponse > get(@PathVariable Long id){
        return ResponseEntity.ok(bloodInventoryService.get(id));
    }
}
