package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.example.demo.dto.ApiResponse;
import com.example.demo.model.AppUser;
import com.example.demo.service.AppUserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    
    private final AppUserService appUserService;

    // Constructor injection instead of @Autowired
    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<ApiResponse> createUser(@RequestBody AppUser appUser) {
        ApiResponse response = appUserService.createUser(appUser);
        
        // Controller maps business responses to HTTP status codes
        if (response.getMessage().contains("required") || 
            response.getMessage().contains("already exists")) {
            return ResponseEntity.badRequest().body(response);
        } else if (response.getMessage().contains("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        
        return ResponseEntity.ok(response);
    }
}
