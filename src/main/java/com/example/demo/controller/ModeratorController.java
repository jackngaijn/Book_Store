package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.model.Moderator;
import com.example.demo.service.ModeratorService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/moderator")
public class ModeratorController {
    @Autowired
    private ModeratorService moderatorService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Moderator moderator) {
        // Validate input
        if (moderator.getUsername() == null || moderator.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("{\"message\": \"Username is required!\"}");
        }
        
        if (moderator.getPassword() == null || moderator.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("{\"message\": \"Password is required!\"}");
        }
        
        try {
            moderatorService.registerNewModerator(moderator.getUsername().trim(), moderator.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Registration successful!\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("{\"message\": \"Hello, World!\"}");
    }
}