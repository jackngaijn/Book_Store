package com.example.demo.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import com.example.demo.model.AppUser;
import com.example.demo.model.Admin;

public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;
    private String userType; // "USER" or "ADMIN"
    
    // Constructor for Admin with specific roles
    public CustomUserDetails(Admin admin, Collection<String> roleNames) {
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.enabled = admin.isEnabled();
        this.userType = "ADMIN";
        this.authorities = roleNames.stream()
            .map(roleName -> new SimpleGrantedAuthority(roleName))
            .toList();
    }
    
    // Constructor for User with specific roles
    public CustomUserDetails(AppUser user, Collection<String> roleNames) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.userType = "USER";
        this.authorities = roleNames.stream()
            .map(roleName -> new SimpleGrantedAuthority(roleName))
            .toList();
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    public String getUserType() {
        return userType;
    }
}