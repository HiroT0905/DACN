package com.example.DACN.service;


import com.example.DACN.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String cccd) throws UsernameNotFoundException {
        return usersRepo.findUserByUsername(cccd)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with CCCD: " + cccd));

    }
}
