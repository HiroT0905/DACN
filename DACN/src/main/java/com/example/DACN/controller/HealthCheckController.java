package com.example.DACN.controller;


import com.example.DACN.dto.HealthMetrics;
import com.example.DACN.model.status.HealthCheckResult;
import com.example.DACN.service.impl.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthCheck")
public class HealthCheckController {

    @Autowired
    private HealthCheckService healthcheckService;

}
