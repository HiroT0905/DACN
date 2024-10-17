package com.example.DACN.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_cccd", referencedColumnName = "CCCD")
    private User user;
    private String fullName;
    private LocalDate dob;
    private String sex;
    private String phone;
    private String address;
    private String email;
}
