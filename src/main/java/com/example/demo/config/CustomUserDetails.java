package com.example.demo.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import com.example.demo.model.AppUser;

import lombok.Getter;

import com.example.demo.model.Admin;

@Getter
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;
    
    // Constructor for Admin with specific roles
    public CustomUserDetails(Admin admin, Collection<String> roleNames) {
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.enabled = admin.isEnabled();
        this.authorities = roleNames.stream()
            .map(roleName -> new SimpleGrantedAuthority(roleName))
            .toList();
    }
    
    // Constructor for User with specific roles
    public CustomUserDetails(AppUser user, Collection<String> roleNames) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.authorities = roleNames.stream()
            .map(roleName -> new SimpleGrantedAuthority(roleName))
            .toList();
    }
}