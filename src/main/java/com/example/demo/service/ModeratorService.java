package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;
import java.time.LocalDateTime;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.example.demo.model.Moderator;
import com.example.demo.repository.ModeratorRepository;

@Service
public class ModeratorService implements UserDetailsService {
    @Autowired
    private ModeratorRepository moderatorRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Moderator moderator = moderatorRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // Check if moderator account is enabled
        if (!moderator.isEnabled()) {
            throw new UsernameNotFoundException("Account is disabled");
        }
        
        return new User(
            moderator.getUsername(),
            moderator.getPassword(),
            moderator.isEnabled(),
            true, // account not expired
            true, // credentials not expired
            true, // account not locked
            Collections.singleton(new SimpleGrantedAuthority("ROLE_MODERATOR"))
        );
    }

    public Moderator registerNewModerator(String username, String password) {
        // Check if username already exists
        if (moderatorRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        
        // Hash the password using the injected bean
        String hashed = passwordEncoder.encode(password);
        Moderator mod = new Moderator();
        mod.setUsername(username);
        mod.setPassword(hashed);
        mod.setEnabled(true);
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