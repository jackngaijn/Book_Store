package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    
    @GetMapping("/api")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome! Please login to access protected resources.");
        response.put("loginUrl", "/login");
        return response;
    }
    
    @GetMapping("/hello")
    public Map<String, Object> hello() {
        Map<String, Object> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            response.put("message", "Hello, " + auth.getName() + "!");
            response.put("username", auth.getName());
            response.put("authorities", auth.getAuthorities());
            response.put("authenticated", true);
        } else {
            response.put("message", "Hello, World!");
            response.put("authenticated", false);
        }
        return response;
    }
    
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        Map<String, Object> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        response.put("message", "Welcome to the dashboard!");
        response.put("username", auth.getName());
        response.put("authorities", auth.getAuthorities());
        return response;
    }
    
    @GetMapping("/user/profile")
    public Map<String, Object> userProfile() {
        Map<String, Object> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        response.put("message", "User Profile");
        response.put("username", auth.getName());
        response.put("authorities", auth.getAuthorities());
        response.put("userType", "USER");
        return response;
    }
    
    @GetMapping("/admin/panel")
    public Map<String, Object> adminPanel() {
        Map<String, Object> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        response.put("message", "Admin Panel");
        response.put("username", auth.getName());
        response.put("authorities", auth.getAuthorities());
        response.put("userType", "ADMIN");
        return response;
    }
}
