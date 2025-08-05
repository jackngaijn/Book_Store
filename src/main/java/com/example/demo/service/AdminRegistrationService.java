package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.Admin;
import com.example.demo.model.Role;
import com.example.demo.model.AdminRole;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.AdminRoleRepository;
import com.example.demo.dto.ApiResponse;
import java.util.Optional;

@Service
@Transactional
public class AdminRegistrationService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdminRoleRepository adminRoleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public ApiResponse registerAdmin(Admin admin) {
        // Check if admin name already exists, if so return error
        Optional<Admin> existingAdmin = adminRepository.findByUsername("ADMIN_" + admin.getUsername());
        if (existingAdmin.isPresent()) {
            return new ApiResponse("Admin name already exists!", null);
        }

        // Check if role exists, if not create it
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        String username = admin.getUsername();
        String password = admin.getPassword();
        String email = admin.getEmail();
        
        ApiResponse response = new ApiResponse();


        // Create new admin
        Admin newAdmin = new Admin();
        newAdmin.setUsername("ADMIN_" + username);
        newAdmin.setPassword(passwordEncoder.encode(password));
        newAdmin.setEmail(email);
        newAdmin.setEnabled(true);
        adminRepository.save(newAdmin);

        // Create new admin role
        AdminRole adminRole = new AdminRole();
        adminRole.setAdmin(newAdmin);
        adminRole.setRole(roleRepository.findByName("ROLE_ADMIN").get());
        adminRoleRepository.save(adminRole);

        // Return success message
        response.setMessage("Admin " + username + " created successfully");
        response.setData(newAdmin);
        return response;
    }
}   