package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.ApiResponse;
import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.model.Role;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    // Constructor injection instead of @Autowired
    public AppUserService(AppUserRepository appUserRepository, 
                         PasswordEncoder passwordEncoder, 
                         RoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public ApiResponse createUser(AppUser appUser) {
        try {
            // Business validation
            if (appUser.getUsername() == null || appUser.getUsername().trim().isEmpty()) {
                ApiResponse response = new ApiResponse();
                response.setMessage("Username is required!");
                response.setData(null);
                return response;
            }
            
            if (appUser.getPassword() == null || appUser.getPassword().trim().isEmpty()) {
                ApiResponse response = new ApiResponse();
                response.setMessage("Password is required!");
                response.setData(null);
                return response;
            }
            
            // Check if username already exists
            if (appUserRepository.findByUsername(appUser.getUsername()).isPresent()) {
                ApiResponse response = new ApiResponse();
                response.setMessage("Username already exists");
                response.setData(null);
                return response;
            }
            
            // Hash the password using the injected bean
            String hashed = passwordEncoder.encode(appUser.getPassword());
            Role userRole = roleRepository.findByRoleName("APPUSER")
                .orElseThrow(() -> new RuntimeException("APPUSER role not found"));

            AppUser newAppUser = new AppUser();
            newAppUser.setUsername(appUser.getUsername());
            newAppUser.setPassword(hashed);
            newAppUser.setName(appUser.getName());
            newAppUser.setEmail(appUser.getEmail());
            newAppUser.setTelephone(appUser.getTelephone());
            newAppUser.setMobile(appUser.getMobile());
            newAppUser.setAddress(appUser.getAddress());
            newAppUser.setStatus("ACTIVE");
            newAppUser.setCreatedDate(LocalDateTime.now().toString());
            newAppUser.setUpdatedDate(LocalDateTime.now().toString());
            newAppUser.setApprovedBy(null);
            newAppUser.setApprovedDate(null);
            newAppUser.setNumberOfRetries(0);
            newAppUser.setLastLoginDate(null);

            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            newAppUser.setRoles(roles);
            appUserRepository.save(newAppUser);

            ApiResponse response = new ApiResponse();
            response.setMessage("User created successfully");
            response.setData(null);
            return response;
            
        } catch (Exception e) {
            ApiResponse response = new ApiResponse();
            response.setMessage(e.getMessage());
            response.setData(null);
            return response;
        }
    }
}
