package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.AppUserRoleRepository;
import com.example.demo.model.AppUser;
import com.example.demo.model.AppUserRole;
import com.example.demo.config.CustomUserDetails;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.AdminRoleRepository;
import com.example.demo.model.Admin;
import com.example.demo.model.AdminRole;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private AppUserRoleRepository appUserRoleRepository;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private AdminRoleRepository adminRoleRepository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to find user first
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        if (user.isPresent()) {
            AppUser userEntity = user.get();
            
            // Get user roles
            List<AppUserRole> userRoles = appUserRoleRepository.findByAppUserId(userEntity.getId());
            List<String> roleNames = userRoles.stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toList());
            return new CustomUserDetails(userEntity, roleNames);
        }
        
        // If not found, try to find admin
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            Admin adminEntity = admin.get();

            // Get admin roles
            List<AdminRole> adminRoles = adminRoleRepository.findByAdminId(adminEntity.getId());
            List<String> roleNames = adminRoles.stream()
                .map(adminRole -> adminRole.getRole().getName())
                .collect(Collectors.toList());
            return new CustomUserDetails(adminEntity, roleNames);
        }
        
        throw new UsernameNotFoundException("User not found: " + username);
    }
}