package com.example.DACN.service.impl;


import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.DonationUnitDTO;
import com.example.DACN.exception.OurException;
import com.example.DACN.model.DonationUnit;
import com.example.DACN.repository.DonationUnitRepo;
import com.example.DACN.service.AwsS3Service;
import com.example.DACN.service.interfac.IDonationUnitService;
import com.example.DACN.service.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DonationUnitService implements IDonationUnitService {
    @Autowired
    private DonationUnitRepo donationUnitRepo;
    @Autowired
    private AwsS3Service awsS3Service;

    @Override
    public ApiResponse addNewUnit(MultipartFile photo, String name, String location, String email, String phone) {

        ApiResponse response = new ApiResponse();
        try{
            String imageUrl = awsS3Service.saveImageToS3(photo);
            DonationUnit unit = new DonationUnit();
            unit.setName(name);
            unit.setLocation(location);
            unit.setEmail(email);
            unit.setPhone(phone);
            unit.setUnitPhotoUrl(imageUrl);
            DonationUnit savedUnit = donationUnitRepo.save(unit);
            DonationUnitDTO unitDto = Utils.mapUnitEntityToUnitDTO(savedUnit);
            response.setCode(200);
            response.setMessage("Successful");
            response.setDonationUnitDTO(unitDto);

        }catch(Exception e){
            e.printStackTrace();
            response.setCode(500);
            response.setMessage("Error add a new Unit" + e.getMessage());

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
    public ApiResponse deleteUnit(Long id) {
      ApiResponse response = new ApiResponse();
      try{
            donationUnitRepo.findById(id).orElseThrow(()->new OurException("Unit not found"));
            donationUnitRepo. deleteById(id);
            response.setCode(200);
            response.setMessage("Successful");

      }catch(OurException e){
        response.setCode(404);
        response.setMessage(e.getMessage());
      }catch(Exception e){
            response.setCode(500);
            response.setMessage("Error deleting unit" + e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse updateUnit(Long id, String name, String location, String email, MultipartFile photo, String phone) {
      ApiResponse response = new ApiResponse();
      try{
          DonationUnit existUnit = donationUnitRepo.findById(id).orElseThrow();
          existUnit.setName(name);
          existUnit.setLocation(location);
          existUnit.setEmail(email);
          existUnit.setPhone(phone);
          String imageUrl = awsS3Service.saveImageToS3(photo);
          existUnit.setUnitPhotoUrl(imageUrl);

          DonationUnit updatedUnit = donationUnitRepo.save(existUnit);
          DonationUnitDTO dto = Utils.mapUnitEntityToUnitDTO(updatedUnit);
          response.setCode(200);
          response.setMessage("Successful");
            response.setDonationUnitDTO(dto);
      }catch(Exception e ){
          response.setCode(500);
          response.setMessage("Co loi xay ra trong qua trinh cap nhat!!! " + e.getMessage());
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
