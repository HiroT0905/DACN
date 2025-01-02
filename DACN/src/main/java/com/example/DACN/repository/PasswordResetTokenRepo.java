package com.example.DACN.repository;

import com.example.DACN.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepo  extends JpaRepository<PasswordResetToken, Long> {
    public PasswordResetToken findByToken(String token);
    public PasswordResetToken findByUser_Username(String username);

}
