package com.example.DACN.repository;

import com.example.DACN.model.DonationUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationUnitRepo  extends JpaRepository<DonationUnit, Long> {
    public DonationUnit findByName(String name);
}
