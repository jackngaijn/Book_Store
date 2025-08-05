package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.AppUserRepository;
import com.example.demo.model.AppUser;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AdminApprovalService {
    @Autowired
    private AppUserRepository appUserRepository;


    public ResponseEntity<?> adminApproval(Long appuser_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<AppUser> appUser = appUserRepository.findById(appuser_id);
        if(appUser.isPresent()){
            appUser.get().setApprovedBy(auth.getName());
            appUser.get().setApprovedDate(LocalDateTime.now());
            appUserRepository.save(appUser.get());
            return ResponseEntity.ok(Map.of("message", "App user approved"));
        }
        return ResponseEntity.ok(Map.of("message", "Admin approval"));
    }


}
