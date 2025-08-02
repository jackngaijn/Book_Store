package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import com.example.demo.model.AppUser;
import com.example.demo.model.Admin;
import com.example.demo.service.LoginService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/appuser/login")
    public ResponseEntity<?> login(@RequestBody AppUser appUser, HttpServletRequest request) {
        return loginService.appUserLogin(appUser, request);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody Admin admin, HttpServletRequest request) {
        return loginService.adminLogin(admin, request);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            HttpSession session = request.getSession(false); // Get session if exists
            if (session != null) {
                session.invalidate(); // This clears all session data, including authentication
            }
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
        }
        return ResponseEntity.ok(Map.of("message", "Already logged out"));
    }
}