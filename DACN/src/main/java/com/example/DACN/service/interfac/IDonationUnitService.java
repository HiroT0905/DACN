package com.example.DACN.service.interfac;

import com.example.DACN.dto.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDonationUnitService {

    ApiResponse addNewUnit(MultipartFile photo, String name, String location, String email,String phone);

    ApiResponse getAllUnit();
    ApiResponse deleteUnit(Long id);
    ApiResponse updateUnit(Long id, String name, String location, String email, MultipartFile photo, String phone);
    ApiResponse getUnit(Long id);

}

