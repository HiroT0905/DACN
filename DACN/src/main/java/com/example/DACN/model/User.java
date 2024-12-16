package com.example.DACN.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @Column(name = "CCCD", length = 12, unique = true)
    @NotBlank(message = "CCCD is required")
    @Size(min = 12, max = 12, message = "CCCD must be 12 characters")
    private String username;

    String password;
    private String phone;
    private String email;

//    @OneToOne
//    private Role roles;

    @OneToOne
    @JoinColumn(name = "user_info_id", referencedColumnName = "id")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "role_id" , referencedColumnName = "id", nullable = false)
    private Role role ;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
    @JsonManagedReference
    @Column(nullable = true)
    private List<Appointment> appointments = new ArrayList<>();



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Column(nullable = true)
    private Set<BloodDonationHistory> bloodDonationHistories = new HashSet<>();




















    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }


    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    @Override
    public boolean isEnabled(){
        return true;
    }


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY )
//    private String userID;
//
//    @Column(name="CCCD", length = 12, unique = true)
//    @Size(min=12, max = 12, message = "CCCD must be 12 characters")
//    private String CCCD;
//
//    @Column(name = "password")
//    private String password;
//
//    @ManyToOne
//    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
//    private Role role;

}
