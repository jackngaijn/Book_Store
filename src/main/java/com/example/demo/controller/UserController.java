package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import com.example.demo.service.UserService;
import com.example.demo.service.UserService.AuthenticationResult;
import com.example.demo.service.UserService.RegistrationResult;
import com.example.demo.model.Book;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        return "user/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {

        AuthenticationResult result = userService.authenticateUser(username, password);
        
        if (result.isSuccess()) {
            session.setAttribute("user", username);
            return "redirect:/user/books";
        } else {
            redirectAttributes.addFlashAttribute("error", result.getMessage());
            return "redirect:/user/login";
        }
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        session.invalidate();
        return "user/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String telephone,
                                @RequestParam String mobile, 
                                @RequestParam String address, 
                                RedirectAttributes redirectAttributes) {
        
        RegistrationResult result = userService.registerUser(username, password, name, email, telephone, mobile, address);
        
        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("success", result.getMessage());
            return "redirect:/user/login";
        } else {
            redirectAttributes.addFlashAttribute("error", result.getMessage());
            return "redirect:/user/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    @GetMapping("/books")
    public String showUserBooks(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/user/login";
        }
        Set<Book> books = userService.getUserWithBooks(session.getAttribute("user").toString());
        model.addAttribute("books", books);
        model.addAttribute("user", session.getAttribute("user"));
        return "user/user_books";
    }

}