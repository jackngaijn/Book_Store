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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginService(AuthenticationManager authenticationManager, AppUserRepository appUserRepository) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
    }

    public ResponseEntity<?> appUserLogin(AppUser appUser, HttpServletRequest request) {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername("USER_" + appUser.getUsername());
        if(!appUserOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "user not found"));
                }
        AppUser user = appUserOptional.get();
        // check if password is correct
        System.out.println("user.getPassword(): " + user.getPassword());
        System.out.println("appUser.getPassword(): " + passwordEncoder.encode(appUser.getPassword()));
        if(!user.getPassword().equals(passwordEncoder.encode(appUser.getPassword()))){
            user.setNumberOfRetries(user.getNumberOfRetries() + 1);
            appUserRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK)
            .body(Map.of("message", "Invalid username or password"));
        }
        // check if user is approved
        if(user.getApprovedBy() == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "You account was not approved"));
        }
        // check if user is locked
        if(user.getNumberOfRetries() >= 5){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "You account was locked due to too many login attempts"));
        }


        


        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    "USER_" + appUser.getUsername(),
                    appUser.getPassword()
                )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Store authentication in session
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
            return ResponseEntity.ok(Map.of(
                "username", appUser.getUsername(),
                "roles", authentication.getAuthorities()
            ));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }
    }
    
    public ResponseEntity<?> adminLogin(Admin admin, HttpServletRequest request) {
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
                "username", admin.getUsername(),
                "roles", authentication.getAuthorities()
            ));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }
}
