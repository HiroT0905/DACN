package com.example.DACN.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @Column(name = "CCCD", length = 12, unique = true)
    @NotBlank(message = "CCCD is required")
    @Size(min = 12, max = 12, message = "CCCD must be 12 characters")
    private String username;

    String password;

//    @OneToOne
//    private Role roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private UserInfo userInfo;
}
