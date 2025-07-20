package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import com.example.demo.service.ModeratorService;
import com.example.demo.service.ModeratorService.AuthenticationResult;
import com.example.demo.service.ModeratorService.RegistrationResult;

@Controller
@RequestMapping("/admin")
public class ModeratorController {
    
    @Autowired
    private ModeratorService moderatorService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("moderator") != null) {
            return "redirect:/admin/dashboard";
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {


        
        AuthenticationResult result = moderatorService.authenticateModerator(username, password);

        if (result.isSuccess()) {
            session.setAttribute("moderator", username);
            return "redirect:/admin/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", result.getMessage());
            return "redirect:/admin/login";
        }
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        session.invalidate();
        return "admin/register";
    }

    @PostMapping("/register") 
    public String registerSubmit(@RequestParam String username,
                               @RequestParam String password,
                               RedirectAttributes redirectAttributes) {

        RegistrationResult result = moderatorService.registerModerator(username, password);
        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("success", result.getMessage());
            return "redirect:/admin/login";
        } else {
            redirectAttributes.addFlashAttribute("error", result.getMessage());
            return "redirect:/admin/register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("moderator") == null) {
            return "redirect:/admin/login";
        }

        List<User> users = moderatorService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @GetMapping("/approve/{username}")
    public String approveUser(@PathVariable String username, 
                              HttpSession session, 
                              RedirectAttributes redirectAttributes) {
        if (session.getAttribute("moderator") == null) {
            return "redirect:/admin/login";
        }

        moderatorService.approveUser(username, session.getAttribute("moderator").toString());
        redirectAttributes.addFlashAttribute("success", "User " + username + " has been approved.");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/lock/{username}")
    public String rejectUser(@PathVariable String username, 
                             HttpSession session, 
                             RedirectAttributes redirectAttributes) {
        if (session.getAttribute("moderator") == null) {
            return "redirect:/admin/login";
        }

        moderatorService.lockUser(username);
        redirectAttributes.addFlashAttribute("success", "User " + username + " has been locked.");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/unlock/{username}")
    public String unlockUser(@PathVariable String username, 
                             HttpSession session, 
                             RedirectAttributes redirectAttributes) {
        if (session.getAttribute("moderator") == null) {
            return "redirect:/admin/login";
        }

        moderatorService.unlockUser(username);
        redirectAttributes.addFlashAttribute("success", "User " + username + " has been unlocked.");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/reset/{username}")
    public String resetRetries(@PathVariable String username, 
                               HttpSession session, 
                               RedirectAttributes redirectAttributes) {
        if (session.getAttribute("moderator") == null) {
            return "redirect:/admin/login";
        }

        moderatorService.resetRetries(username);
        redirectAttributes.addFlashAttribute("success", "Retry count reset for user " + username + ".");
        return "redirect:/admin/dashboard";
    }
}
