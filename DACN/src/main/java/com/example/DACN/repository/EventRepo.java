package com.example.DACN.repository;


import com.example.DACN.model.Event;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<com.example.DACN.model.Event, Long> {

    public List<Event> findEventByEventDate(LocalDate date);
    public List<Event>findEventByEventDateBetweenAndDonationUnit_Id(LocalDate startDate,LocalDate endDate, Long donationUnit_id);


    @Query("SELECT e FROM Event e WHERE e.donationUnit.location = :location " +
            "AND e.eventEndTime > :startTime AND e.eventStartTime < :endTime")
    List<Event> findConflictingEvents(
            @Param("location") String location,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    List<Event> findByDonationUnitId(Long donationUnitId);

    // Xóa tất cả các sự kiện liên quan đến DonationUnit
    void deleteByDonationUnitId(Long donationUnitId);

    public List<Event> findEventsByEventDateBetween(LocalDate startDate, LocalDate endDate);
}