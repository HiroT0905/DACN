package com.example.DACN.controller;


import com.example.DACN.dto.ApiResponse;
import com.example.DACN.service.impl.FaqService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.Map;

@RestController
@RequestMapping("/faq")
public class FaqController {


    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addFaq(@RequestBody Map<String, String> faqData) {
        // Truy cập các giá trị từ Map
        String title = faqData.get("title");
        String description = faqData.get("description");

        // Logic thêm FAQ vào database
        // Ví dụ lưu vào database hoặc thực hiện các công việc khác

        return ResponseEntity.ok(faqService.addFaq(title,description));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateFaq(@PathVariable Long id,@RequestParam(value = "title") String title, @RequestParam(value = "description") String description){
        return ResponseEntity.ok(faqService.updateFaq(id,title,description));

    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getFaq(@PathVariable Long id){
        return ResponseEntity.ok(faqService.getFaqById(id));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAll(){
        return ResponseEntity.ok(faqService.getAllFaq());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteId(@PathVariable Long id){
        return ResponseEntity.ok(faqService.deleteFaq(id));
    }

}
