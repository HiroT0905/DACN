package com.example.DACN.service.interfac;

import com.example.DACN.dto.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IDonationUnitService {

    ApiResponse addNewUnit(Map<String,Object> unitData,MultipartFile photo);

    ApiResponse getAllUnit();
    ApiResponse deleteDonationUnit(Long id);
    ApiResponse updateUnit(Long id, Map<String, Object> unitData , MultipartFile photo);
    ApiResponse getUnit(Long id);

}

