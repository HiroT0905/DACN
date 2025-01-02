package com.example.DACN.service.impl;


import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.DonationUnitDTO;
import com.example.DACN.exception.BloodDonationUnitValidator;
import com.example.DACN.exception.OurException;
import com.example.DACN.model.Appointment;
import com.example.DACN.model.DonationUnit;
import com.example.DACN.model.Event;
import com.example.DACN.repository.AppointmentRepo;
import com.example.DACN.repository.BloodInventoryRepo;
import com.example.DACN.repository.DonationUnitRepo;
import com.example.DACN.repository.EventRepo;
import com.example.DACN.service.AwsS3Service;
import com.example.DACN.service.interfac.IDonationUnitService;
import com.example.DACN.service.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DonationUnitService implements IDonationUnitService {
    @Autowired
    private DonationUnitRepo donationUnitRepo;
    @Autowired
    private AwsS3Service awsS3Service;
    private EventRepo eventRepo;
    @Autowired
    private EventService eventService;
    @Autowired
    private BloodInventoryRepo bloodInventoryRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;

    @Override
    public ApiResponse addNewUnit(Map<String, Object> unitData, MultipartFile photo) {
        ApiResponse response = new ApiResponse();
        try {
            // Kiểm tra dữ liệu đầu vào
            if (unitData.get("name") == null || unitData.get("location") == null ||
                    unitData.get("email") == null || unitData.get("phone") == null ||
                    (photo == null || photo.isEmpty())) {
                throw new RuntimeException("All fields (name, location, email, phone, photo) are required");
            }

            // Tạo mới đối tượng DonationUnit
            DonationUnit newUnit = new DonationUnit();
            newUnit.setName((String) unitData.get("name"));
            newUnit.setLocation((String) unitData.get("location"));
            newUnit.setEmail((String) unitData.get("email"));
            newUnit.setPhone((String) unitData.get("phone"));


            if (photo != null && !photo.isEmpty()) {
                String photoUrl = awsS3Service.saveImageToS3(photo);
                newUnit.setUnitPhotoUrl(photoUrl);
            }

            BloodDonationUnitValidator.validateUnit(newUnit);

            DonationUnit savedUnit = donationUnitRepo.save(newUnit);

            // Tạo response
            DonationUnitDTO dto = Utils.mapUnitEntityToUnitDTO(savedUnit);
            response.setCode(201);
            response.setMessage("Add successful");
            response.setDonationUnitDTO(dto);

        } catch (Exception e) {
            response.setCode(500);
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }


    @Override
    public ApiResponse getAllUnit() {
        ApiResponse response  = new ApiResponse();
        try{
            List<DonationUnit> units = donationUnitRepo.findAll();
            List<DonationUnitDTO> unitDTOList = Utils.mapUnitListEntityToDTO(units);
            if(!units.isEmpty()) {
                response.setCode(200);
                response.setMessage("Successful");
                response.setDonationUnitList(unitDTOList);
            }else{
                response.setCode(401);
                response.setMessage("No units found");
            }

        }catch(Exception e){
            response.setCode(500);
            response.setMessage("Error retrieving all donation units" + e.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public ApiResponse deleteDonationUnit(Long unitId) {

        ApiResponse response = new ApiResponse();
        try{
            // 6. Cuối cùng, xóa DonationUnit
            donationUnitRepo.deleteById(unitId);

            response.setCode(200);
            response.setMessage("Xóa thành công!!!");
            return response;
        }catch(Exception e){
            response.setCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
        // 1. Lấy tất cả các Event liên quan đến DonationUnit




    }

//    @Override
//    public ApiResponse updateUnit(Long id, String name, String location, String email, MultipartFile photo, String phone) {
//      ApiResponse response = new ApiResponse();
//        String imageUrl = null;
//        try{
//          DonationUnit existUnit = donationUnitRepo.findById(id).orElseThrow();
//          existUnit.setName(name);
//          existUnit.setLocation(location);
//          existUnit.setEmail(email);
//          existUnit.setPhone(phone);
//
//
//          if (photo != null && !photo.isEmpty()) {
//              try {
//                  // Process the image (upload to S3 or store locally)
//                  imageUrl = awsS3Service.saveImageToS3(photo); // Assuming your method for uploading the image
//              } catch (Exception e) {
//                  // Handle image upload failure
//                  response.setCode(500);
//                  response.setMessage("Error uploading image: " + e.getMessage());
//                  return response;
//              }
//          } else {
//              // If no new photo, retain the existing one
//              imageUrl = existUnit.getUnitPhotoUrl();
//          }
//          existUnit.setUnitPhotoUrl(imageUrl);
//          DonationUnit updatedUnit = donationUnitRepo.save(existUnit);
//          DonationUnitDTO dto = Utils.mapUnitEntityToUnitDTO(updatedUnit);
//          response.setCode(200);
//          response.setMessage("Successful");
//            response.setDonationUnitDTO(dto);
//      }catch(Exception e ){
//          response.setCode(500);
//          response.setMessage("Co loi xay ra trong qua trinh cap nhat!!! " + e.getMessage());
//      }
//      return response;
//    }
public ApiResponse updateUnit(Long id, Map<String, Object> unitData, MultipartFile newPhoto) {
    ApiResponse response = new ApiResponse();
    try {
        // Lấy thông tin đơn vị hiến máu hiện tại
        DonationUnit existingUnit = donationUnitRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        // Cập nhật thông tin cơ bản
        existingUnit.setName((String) unitData.get("name"));
        existingUnit.setLocation((String) unitData.get("location"));
        existingUnit.setEmail((String) unitData.get("email"));
        existingUnit.setPhone((String) unitData.get("phone"));

        // Cập nhật ảnh nếu có ảnh mới
        if (newPhoto != null && !newPhoto.isEmpty()) {
            String updatedPhotoUrl = awsS3Service.updateImageToS3(newPhoto, existingUnit.getUnitPhotoUrl());
            existingUnit.setUnitPhotoUrl(updatedPhotoUrl);
        }
        BloodDonationUnitValidator.validateUnit(existingUnit);

        // Lưu đơn vị hiến máu
        DonationUnit updatedUnit = donationUnitRepo.save(existingUnit);

        // Tạo response
        DonationUnitDTO dto = Utils.mapUnitEntityToUnitDTO(updatedUnit);
        response.setCode(200);
        response.setMessage("Update successful");
        response.setDonationUnitDTO(dto);

    } catch (Exception e) {
        response.setCode(500);
        response.setMessage("Error: " + e.getMessage());
    }
    return response;
}

    @Override
    public ApiResponse getUnit(Long id) {
        ApiResponse response = new ApiResponse();
        try{
            DonationUnit unitById = donationUnitRepo.findById(id).orElseThrow(()-> new OurException("Unit not found"));

            DonationUnitDTO unitDTO = Utils.mapUnitEntityToUnitDTO(unitById);
            response.setCode(200);
            response.setMessage("Successfully");
            response.setDonationUnitDTO(unitDTO);

        }catch(Exception e ){
            response.setCode(500);
            response.setMessage("Loi xay ra trong qua trinh tim kiem " + e.getMessage());
        }
        return response;
    }

}
