package com.example.DACN.model;


import com.example.DACN.model.status.EventStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;
    private LocalDate eventDate;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;

    private Long maxRegistrations;
    private Long currentRegistrations;

    private EventStatus status = EventStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "donationUnit_id",nullable = false)
    private DonationUnit donationUnit;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
//    @JsonIgnore
    @JsonManagedReference
    private List<Appointment> appointments;


    @Transient
    public String getEventName(){
        if (donationUnit!=null){
            return "Hiến máu " + donationUnit.getName() ;
        }
        return " ";
    }

    @Transient
    public String getEventByUnitName(){
        if (donationUnit!=null){
            return donationUnit.getName();
        }
        return "Không tìm thấy ";
    }

    @Transient
    public String getLocation(){
        if (donationUnit != null){
            return donationUnit.getLocation();
        }
    return " ";
    }
}
