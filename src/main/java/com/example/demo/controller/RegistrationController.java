package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.service.AdminRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Admin;
import com.example.demo.service.AppUserRegistrationService;
import com.example.demo.model.AppUser;

@RestController
public class RegistrationController {
    @Autowired
    private AdminRegistrationService adminRegistrationService;

    @Autowired
    private AppUserRegistrationService appUserRegistrationService;

    @PostMapping("/admin/register")
    public ResponseEntity<ApiResponse> createAdmin(@Valid @RequestBody Admin admin) {
        ApiResponse response = adminRegistrationService.registerAdmin(admin);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/appuser/register")
    public ResponseEntity<ApiResponse> createAppUser(@Valid @RequestBody AppUser appUser) {
        ApiResponse response = appUserRegistrationService.registerAppUser(appUser);
        return ResponseEntity.ok(response);
    }
}
