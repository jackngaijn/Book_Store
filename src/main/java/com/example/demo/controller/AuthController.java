package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.model.Moderator;
import com.example.demo.service.ModeratorService;

@Controller
public class AuthController {
    @Autowired
    private ModeratorService moderatorService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("moderator", new Moderator());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Moderator moderator, Model model) {
        // Validate input
        if (moderator.getUsername() == null) {
            model.addAttribute("error", "Username is required!");
            return "register";
        }
        
        if (moderator.getPassword() == null) {
            model.addAttribute("error", "Password is required!");
            return "register";
        }
        
        try {
            moderatorService.registerNewModerator(moderator.getUsername().trim(), moderator.getPassword());
            return "redirect:/login?registered";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}