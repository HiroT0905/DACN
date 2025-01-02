package com.example.DACN.dto;

import lombok.Data;

import java.time.LocalDateTime;



@Data
public class FaqDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime timestamp;

}
