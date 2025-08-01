package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.model.AppUser;
import com.example.demo.config.CustomUserDetails;
import com.example.demo.repository.AdminRepository;
import com.example.demo.model.Admin;
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to find user first
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        if (user.isPresent()) {
            return new CustomUserDetails(user.get());
        }
        
        // If not found, try to find admin
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return new CustomUserDetails(admin.get());
        }
        
        throw new UsernameNotFoundException("User not found: " + username);
    }
}