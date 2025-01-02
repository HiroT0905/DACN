package com.example.DACN.controller;


import com.example.DACN.dto.ApiResponse;
import com.example.DACN.dto.NewsDTO;
import com.example.DACN.service.impl.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNews(@RequestParam("title") String title,
                                               @RequestParam("content") String content,
                                               @RequestParam("author") String author,
                                               @RequestParam("image") MultipartFile image) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle(title);
        newsDTO.setContent(content);
        newsDTO.setAuthor(author);

        ApiResponse response = newsService.addNews(newsDTO, image);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    // Cập nhật bài viết
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateNews(@PathVariable Long id,
                                                  @RequestParam("title") String title,
                                                  @RequestParam("content") String content,
                                                  @RequestParam("author") String author,
                                                  @RequestParam(value = "image", required = false) MultipartFile image) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle(title);
        newsDTO.setContent(content);
        newsDTO.setAuthor(author);

        ApiResponse response = newsService.updateNews(id, newsDTO, image);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    // Lấy tất cả bài viết
    @GetMapping
    public ResponseEntity<ApiResponse> getAllNews() {
        ApiResponse response = newsService.getAllNews();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    // Lấy bài viết theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getNewsById(@PathVariable Long id) {
        ApiResponse response = newsService.getNewsById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    // Xóa bài viết
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteNews(@PathVariable Long id) {
        ApiResponse response = newsService.deleteNews(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }


}
