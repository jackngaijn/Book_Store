package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.example.demo.model.Moderator;
import com.example.demo.model.Role;
import com.example.demo.repository.ModeratorRepository;
import com.example.demo.repository.RoleRepository;
import java.util.Set;
import java.util.HashSet;

@Service
public class ModeratorService {
    @Autowired
    private ModeratorRepository moderatorRepo;

    @Autowired
    private RoleRepository roleRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // loadUserByUsername method moved to UnifiedAuthService

    public Moderator registerNewModerator(String username, String password) {
        // Check if username already exists
        if (moderatorRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        
        // Hash the password using the injected bean
        String hashed = passwordEncoder.encode(password);
        Role moderatorRole = roleRepo.findByRoleName("MODERATOR").orElseThrow(() -> new RuntimeException("Role not found"));
        Moderator mod = new Moderator();
        mod.setUsername(username);
        mod.setPassword(hashed);
        mod.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        roles.add(moderatorRole);
        mod.setRoles(roles);
        
        return moderatorRepo.save(mod);
    }


    public void updateLastLoginDate(String username) {
        Moderator moderator = moderatorRepo.findByUsername(username).orElse(null);
        if (moderator != null) {
            moderator.setLastLoginDate(LocalDateTime.now());
            moderatorRepo.save(moderator);
        }
    }
}