package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import com.example.demo.service.AdminApprovalService;
import com.example.demo.model.AppUser;  
import com.example.demo.model.Admin;
import com.example.demo.service.LoginService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.repository.AppUserRepository;
import java.util.List;
import java.util.Optional;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private AdminApprovalService adminApprovalService;

    @Autowired
    private AppUserRepository appUserRepository;

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

    @GetMapping("/logout")
    public ResponseEntity<?> logoutAll(HttpServletRequest request) {
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

    @GetMapping("/check-admin")
    public ResponseEntity<Boolean> checkAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(isAdmin);
    }

    @GetMapping("/check-user")
    public ResponseEntity<Boolean> checkUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isUser = auth.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
        return ResponseEntity.ok(isUser);
    }

    @GetMapping("/current-user-id")
    public ResponseEntity<Long> getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            String username = auth.getName();
            // Find user by username and return ID
            Optional<AppUser> appUser = appUserRepository.findByUsername(username);
            if (appUser.isPresent()) {
                return ResponseEntity.ok(appUser.get().getId());
            }
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/appusers")
    public ResponseEntity<List<AppUser>> getAllAppUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Check if user is admin
        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        
        if (!isAdmin) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        
        List<AppUser> appUsers = appUserRepository.findAll();
        return ResponseEntity.ok(appUsers);
    }

    // ################################ admin approval ################################
    // approve appuser account api
    @GetMapping("/admin/approve/{appuser_id}")
    public ResponseEntity<?> adminApproval(@PathVariable Long appuser_id) {
        return adminApprovalService.adminApproval(appuser_id);
    }

    // ################################ admin approval ################################
}