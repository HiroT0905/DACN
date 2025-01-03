package com.example.DACN.repository;

import com.example.DACN.model.Healthcheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCheckRepo extends JpaRepository<Healthcheck, Long> {
}
