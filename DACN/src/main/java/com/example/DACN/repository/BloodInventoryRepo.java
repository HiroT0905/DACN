package com.example.DACN.repository;

import com.example.DACN.model.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BloodInventoryRepo  extends JpaRepository<BloodInventory,Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM BloodInventory b WHERE b.appointment.Id IN :appointmentIds")
    void deleteByAppointmentIdIn(@Param("appointmentIds") List<Long> appointmentIds);
}
