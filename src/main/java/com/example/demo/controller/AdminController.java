package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.service.AdminRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Admin;

@Controller
public class AdminController {
    
    @Autowired
    private AdminRegistrationService adminRegistrationService;

    
    // @PostMapping("/admin/register")
    // public String registerAdmin(
    //         @RequestParam String username,
    //         @RequestParam String password,
    //         @RequestParam String confirmPassword,
    //         @RequestParam String email,
    //         RedirectAttributes redirectAttributes) {
        
    //     try {
    //         // Validate password confirmation
    //         if (!password.equals(confirmPassword)) {
    //             redirectAttributes.addFlashAttribute("error", "Passwords do not match!");
    //             return "redirect:/admin/register";
    //         }
            
    //         // Register the admin
    //         adminRegistrationService.registerAdmin(username, password, email);
    //         redirectAttributes.addFlashAttribute("success", "Admin registered successfully! You can now login.");
    //         return "redirect:/login";
            
    //     } catch (RuntimeException e) {
    //         redirectAttributes.addFlashAttribute("error", e.getMessage());
    //         return "redirect:/admin/register";
    //     }
    // }

    @PostMapping("/admin/register")
    public ResponseEntity<ApiResponse> createAdmin(@RequestBody Admin admin) {
            ApiResponse response = adminRegistrationService.registerAdmin(admin);
            return ResponseEntity.ok(response);
    }
} 