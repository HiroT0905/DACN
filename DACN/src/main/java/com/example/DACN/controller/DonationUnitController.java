package com.example.DACN.controller;


import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.DonationUnitDTO;
import com.example.DACN.exception.BloodDonationUnitValidator;
import com.example.DACN.exception.OurException;
import com.example.DACN.model.DonationUnit;
import com.example.DACN.repository.DonationUnitRepo;
import com.example.DACN.service.AwsS3Service;
import com.example.DACN.service.impl.DonationUnitService;
import com.example.DACN.service.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/units")
public class DonationUnitController {
    private final DonationUnitService donationUnitService;
    private final DonationUnitRepo donationUnitRepo;

    @Autowired
    private AwsS3Service awsS3Service;
    public DonationUnitController(DonationUnitService donationUnitService, DonationUnitRepo donationUnitRepo) {
        this.donationUnitService = donationUnitService;
        this.donationUnitRepo = donationUnitRepo;
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
    ) {
        Map<String, Object> unitData = new HashMap<>();
        unitData.put("name", name);
        unitData.put("location", location);
        unitData.put("email", email);
        unitData.put("phone", phone);

        ApiResponse response = donationUnitService.addNewUnit(unitData, photo);
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
    public ResponseEntity<ApiResponse> updateDonationUnit(
            @PathVariable Long id, // Nhận ID của đơn vị cần cập nhật
            @RequestParam(required = false) MultipartFile photo, // Ảnh mới (có thể không có)
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam String email,
            @RequestParam String phone
    ) {
        ApiResponse response = new ApiResponse();

        // Kiểm tra xem đơn vị có tồn tại hay không
        DonationUnit existingUnit = donationUnitRepo.findById(id).orElse(null);
        if (existingUnit == null) {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(404);
            apiResponse.setMessage("Donation unit not found");
            return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
        }

        // Nếu có ảnh mới, lưu lên S3
        String photoUrl = existingUnit.getUnitPhotoUrl(); // Giữ URL ảnh cũ nếu không có ảnh mới
        if (photo != null && !photo.isEmpty()) {
            // Lưu ảnh mới lên S3 và lấy URL
            photoUrl = awsS3Service.saveImageToS3(photo);
        }
        // Cập nhật thông tin của đơn vị
        existingUnit.setName(name);
        existingUnit.setLocation(location);
        existingUnit.setEmail(email);
        existingUnit.setPhone(phone);
        existingUnit.setUnitPhotoUrl(photoUrl); // Cập nhật ảnh mới nếu có
        try {
            BloodDonationUnitValidator.validateUnit(existingUnit);
        } catch (OurException ex) {
            // Ném lỗi và trả về thông báo cho client
            response.setCode(400); // Lỗi người dùng gửi dữ liệu không hợp lệ
            response.setMessage(ex.getMessage()); // Thông báo lỗi từ ngoại lệ
            return ResponseEntity.status(response.getCode()).body(response);
        }
        // Lưu đơn vị đã cập nhật vào cơ sở dữ liệu
        donationUnitRepo.save(existingUnit);
        response.setCode(200);
        response.setMessage("Donation unit updated successfully");
        return ResponseEntity.status(response.getCode()).body(response);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse > deleteUnit(@PathVariable Long id){
        return ResponseEntity.ok(donationUnitService.deleteDonationUnit(id));
    }
}
