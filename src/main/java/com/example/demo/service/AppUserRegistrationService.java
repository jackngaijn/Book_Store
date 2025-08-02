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
        String username = appUser.getUsername();
        String password = appUser.getPassword();
        String email = appUser.getEmail();
        
        ApiResponse response = new ApiResponse();

        // Check if username already exists, if so return error
        if (appUserRepository.findByUsername(username).isPresent()) {
            response.setMessage("Username already exists!");
            response.setData(null);
            return response;
        }
        
        // Check if role exists, if not create it
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }
        
        // Create new app user
        AppUser newAppUser = new AppUser();
        newAppUser.setUsername(username);
        newAppUser.setPassword(passwordEncoder.encode(password));
        newAppUser.setEmail(email);
        newAppUser.setEnabled(true);

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
