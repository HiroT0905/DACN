package com.example.DACN.repository;

import com.example.DACN.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepo  extends JpaRepository<News, Long> {
}
