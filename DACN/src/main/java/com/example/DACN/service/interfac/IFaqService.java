package com.example.DACN.service.interfac;

import com.example.DACN.dto.ApiResponse;
import com.example.DACN.model.Faq;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IFaqService {

    ApiResponse addFaq(String title, String description);
    ApiResponse getAllFaq();
    ApiResponse deleteFaq(Long id);
    ApiResponse updateFaq(Long id, String title, String description);
    ApiResponse getFaqById(Long id);

}
