package com.example.DACN.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class NewsDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private  String images;
    private LocalDateTime timestamp;
}
