package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.example.demo.model.Moderator;
import com.example.demo.model.Role;
import com.example.demo.repository.ModeratorRepository;
import com.example.demo.repository.RoleRepository;
import java.util.Set;
import java.util.HashSet;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.example.demo.dto.ApiResponse;

@Service
public class ModeratorService {
    @Autowired
    private ModeratorRepository moderatorRepo;

    @Autowired
    private RoleRepository roleRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<ApiResponse> registerNewModerator(Moderator moderator) {
        // Validate input
        if (moderator.getUsername() == null || moderator.getUsername().trim().isEmpty()) {
            ApiResponse response = new ApiResponse();
            response.setMessage("Username is required!");
            response.setData(null);
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
        }
        
        if (moderator.getPassword() == null || moderator.getPassword().trim().isEmpty()) {
            ApiResponse response = new ApiResponse();
            response.setMessage("Password is required!");
            response.setData(null);
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
        }
        // Check if username already exists
        if (moderatorRepo.findByUsername(moderator.getUsername()).isPresent()) {
            ApiResponse response = new ApiResponse();
            response.setMessage("Username already exists");
            response.setData(null);
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
        }
        
        // Hash the password using the injected bean
        String hashed = passwordEncoder.encode(moderator.getPassword());
        Role moderatorRole = roleRepo.findByRoleName("MODERATOR")
            .orElseThrow(() -> new RuntimeException("MODERATOR role not found"));

        Moderator mod = new Moderator();
        mod.setUsername(moderator.getUsername());
        mod.setPassword(hashed);
        mod.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        roles.add(moderatorRole);
        mod.setRoles(roles);
        moderatorRepo.save(mod);
        
        ApiResponse response = new ApiResponse();
        response.setMessage("Registration successful!");
        response.setData(null);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

    public ResponseEntity<ApiResponse> login(Moderator moderator) {
        // Validate input
        if (moderator.getUsername() == null || moderator.getUsername().trim().isEmpty()) {
            ApiResponse response = new ApiResponse();
            response.setMessage("Username is required!");
            response.setData(null);
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
        }
        
        if (moderator.getPassword() == null || moderator.getPassword().trim().isEmpty()) {
            ApiResponse response = new ApiResponse();
            response.setMessage("Password is required!");
            response.setData(null);
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
        }

        // Check if username exists
        if (!moderatorRepo.findByUsername(moderator.getUsername()).isPresent()) {
            ApiResponse response = new ApiResponse();
            response.setMessage("Username not found");
            response.setData(null);
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
        }

        // Check if password is correct
        if (!passwordEncoder.matches(moderator.getPassword(), moderatorRepo.findByUsername(moderator.getUsername()).get().getPassword())) {
            ApiResponse response = new ApiResponse();
            response.setMessage("Invalid password");
            response.setData(null);
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
        }

        ApiResponse response = new ApiResponse();
        response.setMessage("Login successful!");
        response.setData(null);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }


    public void updateLastLoginDate(String username) {
        Moderator moderator = moderatorRepo.findByUsername(username).orElse(null);
        if (moderator != null) {
            moderator.setLastLoginDate(LocalDateTime.now());
            moderatorRepo.save(moderator);
        }
    }
}