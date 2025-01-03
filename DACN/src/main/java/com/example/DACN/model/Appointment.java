package com.example.DACN.model;


import com.example.DACN.model.status.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id" , referencedColumnName = "id")
    @JsonBackReference
    @ToString.Exclude

    private Event event;

    @ManyToOne(fetch = FetchType    .LAZY)
    @JoinColumn(name = "user_cccd", referencedColumnName = "CCCD")
    @JsonBackReference
    @ToString.Exclude
    private User user;


    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Healthcheck healthcheck;

//    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private BloodInventory bloodInventory;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private BloodDonationHistory bloodDonationHistory;

    @OneToOne
    private BloodInventory  bloodInventory;

    private LocalDateTime appointmentDateTime;

    private Integer bloodAmount;

    private LocalDateTime nextDonationEligibleDate;

    private AppointmentStatus status;
}
