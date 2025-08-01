package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.Admin;
import com.example.demo.model.Role;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.RoleRepository;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import com.example.demo.dto.ApiResponse;

@Service
@Transactional
public class AdminRegistrationService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public ApiResponse registerAdmin(Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        String email = admin.getEmail();
        
        ApiResponse response = new ApiResponse();
        // Check if username already exists
        if (adminRepository.findByUsername(username).isPresent()) {
            response.setMessage("Username already exists!");
            response.setData(null);
            return response;
        }
            
        // Create new admin
        Admin newAdmin = new Admin();
        newAdmin.setUsername(username);
        newAdmin.setPassword(passwordEncoder.encode(password));
        newAdmin.setEmail(email);
        newAdmin.setEnabled(true);
        
        // Assign ADMIN role
        Optional<Role> adminRoleOpt = roleRepository.findByName("ROLE_ADMIN");
        Role adminRole;
        
        if (adminRoleOpt.isPresent()) {
            adminRole = adminRoleOpt.get();
            System.out.println("Found existing ROLE_ADMIN: " + adminRole.getName());
        } else {
            // Create ADMIN role if it doesn't exist
            adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            adminRole = roleRepository.save(adminRole);
            System.out.println("Created new ROLE_ADMIN: " + adminRole.getName());
        }

        
        
        adminRepository.save(newAdmin);
        response.setMessage("Admin " + username + " created successfully");
        response.setData(newAdmin);
        return response;
    }
} 