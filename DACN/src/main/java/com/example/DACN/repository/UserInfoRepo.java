package com.example.DACN.repository;

import com.example.DACN.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepo  extends JpaRepository<UserInfo, Long> {
    UserInfo findByEmail(String email);
    UserInfo getUserInfoByPhone(String phone);
}
