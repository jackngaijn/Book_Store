package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.model.AdminRole;
import com.example.demo.model.AppUserRole;
import java.util.List;

@Controller
public class TestTableController {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/test/admin")
    @ResponseBody
    @Transactional
    public String testAdmin(@RequestParam String username) {
        // test table relationship between admin and admin role, when user input admin then get the admin role
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isEmpty()) {
            return "admin not found";
        }
        
        // Extract role names from AdminRole objects
        String roleNames = admin.get().getRoles().stream()
            .map(adminRole -> adminRole.getRole().getName())
            .collect(Collectors.joining(", "));
            
        return "admin role: " + roleNames;
    }

    @GetMapping("/test/appuser")
    @ResponseBody
    @Transactional
    public String testAppUser(@RequestParam String username) {
        // test table relationship between appuser and appuser role, when user input appuser then get the appuser role
        Optional<AppUser> appuser = appUserRepository.findByUsername(username);
        if (appuser.isEmpty()) {
            return "appuser not found";
        }
        
        // Extract role names from AppUserRole objects
        String roleNames = appuser.get().getRoles().stream()
            .map(appuserRole -> appuserRole.getRole().getName())
            .collect(Collectors.joining(", "));
            
        return "appuser role: " + roleNames;
    }

    @GetMapping("/test/role")
    @ResponseBody
    @Transactional
    public String testAdminRole(@RequestParam String rolename) {
        // test table relationship between admin and admin role, when user input admin then get the admin role
        Optional<Role> role = roleRepository.findByName(rolename);
        if (role.isEmpty()) {
            return "role not found";
        }
        List<AdminRole> adminRoles = role.get().getAdmins();
        List<AppUserRole> appUserRoles = role.get().getAppUsers();
        String adminNames = adminRoles.stream()
            .map(adminRole -> adminRole.getAdmin().getUsername())
            .collect(Collectors.joining(", "));
        String appuserNames = appUserRoles.stream()
            .map(appuserRole -> appuserRole.getAppUser().getUsername())
            .collect(Collectors.joining(", "));
        return "admin role: " + adminNames + " appuser role: " + appuserNames;
    }
}
