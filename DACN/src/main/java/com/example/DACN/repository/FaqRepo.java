package com.example.DACN.repository;


import com.example.DACN.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepo  extends JpaRepository<Faq, Long> {
}
