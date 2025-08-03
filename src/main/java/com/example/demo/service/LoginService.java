package com.example.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Map;
import com.example.demo.model.AppUser;
import com.example.demo.model.Admin;
import org.springframework.stereotype.Service;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.AdminRepository;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final AdminRepository adminRepository;


        public LoginService(AuthenticationManager authenticationManager, AppUserRepository appUserRepository, AdminRepository adminRepository) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.adminRepository = adminRepository;
    }

    public ResponseEntity<?> appUserLogin(AppUser appUser, HttpServletRequest request) {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername("USER_" + appUser.getUsername());
        if(!appUserOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "user not found"));
        }
        AppUser user_from_db = appUserOptional.get();
        
        // Check business rules BEFORE authentication
        if(user_from_db.getApprovedBy() == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "You account was not approved"));
        }
        if(user_from_db.getNumberOfRetries() > 4){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "You account was locked due to too many login attempts"));
        }

        try {
            // This does the REAL password validation
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    "USER_" + appUser.getUsername(),
                    appUser.getPassword()
                )
            );
            
            // Reset retry count on successful login
            user_from_db.setNumberOfRetries(0);
            appUserRepository.save(user_from_db);
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
            
            // For api debugging
            // return ResponseEntity.ok(Map.of(
            //     "username", user.getUsername(),
            //     "roles", authentication.getAuthorities()
            // ));

            // For frontend show message
            return ResponseEntity.ok(Map.of(
                "message", appUser.getUsername() + " logged in successfully"
            ));
        } catch (AuthenticationException ex) {
            // Increment retry count on failed authentication
            user_from_db.setNumberOfRetries(user_from_db.getNumberOfRetries() + 1);
            appUserRepository.save(user_from_db);
            if(user_from_db.getNumberOfRetries() == 5){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "You account was locked due to too many login attempts"));
            }
            if(user_from_db.getNumberOfRetries() < 5){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "You still have " + (5 - user_from_db.getNumberOfRetries()) + " attempts left"));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }
    }
    
    public ResponseEntity<?> adminLogin(Admin admin, HttpServletRequest request) {
        Optional<Admin> adminOptional = adminRepository.findByUsername("ADMIN_" + admin.getUsername());
        if(!adminOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "admin not found"));
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    "ADMIN_" + admin.getUsername(),
                    admin.getPassword()
                )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
                return ResponseEntity.ok(Map.of(
                    "message", admin.getUsername() + " logged in successfully"
                ));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }
    }
}
