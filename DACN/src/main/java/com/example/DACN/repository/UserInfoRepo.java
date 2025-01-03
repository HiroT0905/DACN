package com.example.DACN.repository;

import com.example.DACN.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
//    Optional<UserInfo> findUserInfoByUserUsername(String username);
}
