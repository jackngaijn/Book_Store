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
import org.springframework.security.core.Authentication;

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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @GetMapping("/currentuser")
    public Boolean currentUser(HttpServletRequest request) {
        return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }

    @GetMapping("/debug/auth")
    public ResponseEntity<?> debugAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
            "isAuthenticated", auth.isAuthenticated(),
            "username", auth.getName(),
            "authorities", auth.getAuthorities(),
            "principal", auth.getPrincipal().getClass().getSimpleName()
        ));
    }
}