package com.example.DACN.repository;

import com.example.DACN.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<User, String>
{
    Optional<User> findUserByUsername(String username);
    Boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);
}
