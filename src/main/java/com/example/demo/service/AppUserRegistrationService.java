package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.AppUserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.dto.ApiResponse;
import com.example.demo.model.AppUser;
import com.example.demo.model.Role;
import com.example.demo.model.AppUserRole;
import java.util.Optional;

@Service
@Transactional
public class AppUserRegistrationService {
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AppUserRoleRepository appUserRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public ApiResponse registerAppUser(AppUser appUser) {
        // Check if username already exists, if so return error
        Optional<AppUser> existingUser = appUserRepository.findByUsername("USER_" + appUser.getUsername());
        if (existingUser.isPresent()) {
            return new ApiResponse("Username already exists!", null);
        }
        
        // Check if role exists, if not create it
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }
        
        
        String username = appUser.getUsername();
        String password = appUser.getPassword();
        String name = appUser.getName();
        String email = appUser.getEmail();
        String telephone = appUser.getTelephone();
        String mobile = appUser.getMobile();
        String address = appUser.getAddress();
        java.time.LocalDateTime createdDate = java.time.LocalDateTime.now();
        java.time.LocalDateTime updatedDate = java.time.LocalDateTime.now();
        
        ApiResponse response = new ApiResponse();
        // Create new app user
        AppUser newAppUser = new AppUser();
        newAppUser.setUsername("USER_" + username);
        newAppUser.setPassword(passwordEncoder.encode(password));
        newAppUser.setName(name);
        newAppUser.setEmail(email);
        newAppUser.setTelephone(telephone);
        newAppUser.setMobile(mobile);
        newAppUser.setAddress(address);
        newAppUser.setCreatedDate(createdDate);
        newAppUser.setUpdatedDate(updatedDate);

        // Create new app user role
        AppUserRole appUserRole = new AppUserRole();
        appUserRole.setAppUser(newAppUser);
        appUserRole.setRole(roleRepository.findByName("ROLE_USER").get());
        appUserRoleRepository.save(appUserRole);

        // Save app user
        appUserRepository.save(newAppUser);

        // Return success message
        response.setMessage("App user " + username + " created successfully");
        response.setData(newAppUser);
        return response;
    }
}
