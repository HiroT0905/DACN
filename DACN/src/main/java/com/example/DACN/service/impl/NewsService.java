package com.example.DACN.service.impl;

import com.example.DACN.dto.NewsDTO;
import com.example.DACN.dto.ApiResponse;
import com.example.DACN.exception.OurException;
import com.example.DACN.model.News;

import com.example.DACN.repository.NewsRepo;
import com.example.DACN.service.AwsS3Service;
import com.example.DACN.service.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepo newsRepository;

    @Autowired
    private AwsS3Service awsS3Service;

    // Thêm bài viết và lưu hình ảnh lên S3
    public ApiResponse addNews(NewsDTO newsDTO, MultipartFile image) {
        ApiResponse response = new ApiResponse();
        try {
            News news = new News();
            news.setTitle(newsDTO.getTitle());
            news.setContent(newsDTO.getContent());
            if(newsDTO.getAuthor() == null || newsDTO.getAuthor().isEmpty()){
                news.setAuthor("ADMIN");
            }else
                news.setAuthor(newsDTO.getAuthor());
            news.setTimestamp(LocalDateTime.now());
            //Nếu có hình ảnh, lưu lên S3
            if (image != null && !image.isEmpty()) {
                String imageUrl = awsS3Service.saveImageToS3(image);
                news.setImageUrl(imageUrl);
            }
            News savedNews = newsRepository.save(news);
            NewsDTO newsDTO1 = Utils.mapNewsEntityToNewsDTO(savedNews);
            response.setCode(200);
            response.setMessage("News added successfully.");
            response.setNewsDTO(newsDTO1);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage("An error occurred: " + e.getMessage());
        }
        return response;
    }

    // Cập nhật bài viết và hình ảnh nếu có
    public ApiResponse updateNews(Long id, NewsDTO newsDTO, MultipartFile image) {
        ApiResponse response = new ApiResponse();
        try {
            News existingNews = newsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("News not found"));

            existingNews.setTitle(newsDTO.getTitle());
            existingNews.setContent(newsDTO.getContent());

            if(newsDTO.getAuthor() == null || newsDTO.getAuthor().isEmpty()){
                existingNews.setAuthor("ADMIN");
            }else
                existingNews.setAuthor(newsDTO.getAuthor());

            existingNews.setTimestamp(LocalDateTime.now() );

            // Cập nhật hình ảnh nếu có
            if (image != null && !image.isEmpty()) {
                String imageUrl = awsS3Service.saveImageToS3(image);
                existingNews.setImageUrl(imageUrl);
            }

            News updatedNews = newsRepository.save(existingNews);
            NewsDTO newsDTO1 = Utils.mapNewsEntityToNewsDTO(updatedNews);
            response.setCode(200);
            response.setMessage("News updated successfully.");
            response.setNewsDTO(newsDTO1);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage("An error occurred: " + e.getMessage());
        }
        return response;
    }

    // Lấy tất cả bài viết
    public ApiResponse getAllNews() {
        ApiResponse response = new ApiResponse();
        try {
            List<News> newsList = newsRepository.findAll();
            List<NewsDTO > newsDTOList=  Utils.mapNewsListEntityToDTO(newsList) ;
            response.setCode(200);
            response.setMessage("News fetched successfully.");
            response.setNewsDTOList(newsDTOList);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage("An error occurred: " + e.getMessage());
        }
        return response;
    }

    // Lấy bài viết theo ID
    public ApiResponse getNewsById(Long id) {
        ApiResponse response = new ApiResponse();
        try {
            News news = newsRepository.findById(id).orElseThrow(()-> new OurException("News not found!!!"));
            NewsDTO newsDTO = Utils.mapNewsEntityToNewsDTO(news);
            response.setCode(200);
            response.setMessage("News fetched successfully.");
            response.setNewsDTO(newsDTO);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage("An error occurred: " + e.getMessage());
        }
        return response;
    }

    // Xóa bài viết
    public ApiResponse deleteNews(Long id) {
        ApiResponse response = new ApiResponse();
        try {
            newsRepository.deleteById(id);
            response.setCode(200);
            response.setMessage("News deleted successfully.");
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage("An error occurred: " + e.getMessage());
        }
        return response;
    }
}
