package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.example.demo.model.Moderator;
import com.example.demo.model.AppUser;
import com.example.demo.repository.ModeratorRepository;
import com.example.demo.repository.AppUserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import java.util.Optional;

@Service
public class UnifiedAuthService implements UserDetailsService {
    
    @Autowired
    private ModeratorRepository moderatorRepo;
    
    @Autowired
    private AppUserRepository appUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First try to find as a Moderator
        Optional<Moderator> moderatorOpt = moderatorRepo.findByUsername(username);
        if (moderatorOpt.isPresent()) {
            Moderator moderator = moderatorOpt.get();

            Set<GrantedAuthority> authorities = moderator.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());
            
            return new User(
                moderator.getUsername(),
                moderator.getPassword(),
                moderator.isEnabled(),
                true, true, true,
                authorities
            );
        }
        
        // Then try to find as a regular User
        Optional<AppUser> appUserOpt = appUserRepo.findByUsername(username);
        if (appUserOpt.isPresent()) {
            AppUser appUser = appUserOpt.get();

            Set<GrantedAuthority> authorities = appUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());
            
            return new User(
                appUser.getUsername(),
                appUser.getPassword(),
                true,
                true, true, true,
                authorities
            );
        }
        
        throw new UsernameNotFoundException("User not found: " + username);
    }
} 