package com.example.DACN.controller;


import com.example.DACN.dto.ApiResponse;
import com.example.DACN.model.DonationUnit;
import com.example.DACN.service.impl.DonationUnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/units")
public class DonationUnitController {
    private final DonationUnitService donationUnitService;

    public DonationUnitController(DonationUnitService donationUnitService) {
        this.donationUnitService = donationUnitService;
    }

    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('ADMIN')")

    //dung request param vi nhan du lieu tu form-data
    public ResponseEntity<ApiResponse> addDonationUnit(
            @RequestParam MultipartFile photo,
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam String email,
            @RequestParam String phone
    ){
        if (  photo.isEmpty() || name == null || location == null || email == null || phone == null){
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(400);
            apiResponse.setMessage("Please provide values for all fields(photo, name, location, email, phone");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);

        }
        ApiResponse response = donationUnitService.addNewUnit(photo, name, location, email, phone);
        return ResponseEntity.status(response.getCode()).body(response);
    }
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAllUnits(){
            return ResponseEntity.ok(donationUnitService.getAllUnit());
    }

    @GetMapping("/get-unit/{id}")
    public ResponseEntity<ApiResponse> getUnit(@PathVariable Long id){
        return ResponseEntity.ok(donationUnitService.getUnit(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUnit(@PathVariable Long id, String name, String location, String email,  MultipartFile photo,String phone){
        return ResponseEntity.ok(donationUnitService.updateUnit(id,name,location,email,photo,phone));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse > deleteUnit(@PathVariable Long id){
        return ResponseEntity.ok(donationUnitService.deleteUnit(id));
    }
}
