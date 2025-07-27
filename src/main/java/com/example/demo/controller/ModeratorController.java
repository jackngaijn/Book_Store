package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.model.Moderator;
import com.example.demo.service.ModeratorService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import com.example.demo.dto.ApiResponse;

@RestController
@RequestMapping("/moderator")
public class ModeratorController {
    @Autowired
    private ModeratorService moderatorService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody Moderator moderator) {
        ResponseEntity<ApiResponse> response = moderatorService.registerNewModerator(moderator);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody Moderator moderator) {
        ResponseEntity<ApiResponse> response = moderatorService.login(moderator);
        return response;
    }

    @PostMapping("/test")
    public ResponseEntity<ApiResponse> test(@RequestBody Moderator moderator) {
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ApiResponse("Hello, Worldsdfsdfd!"));
    }
}