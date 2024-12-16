package com.example.DACN.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DonationUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name ;
    private String location;

    private String phone ;

    private String email;

    private String unitPhotoUrl;

    @OneToMany(mappedBy = "donationUnit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Event> events;


}
