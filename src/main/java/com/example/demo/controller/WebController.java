package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.example.demo.repository.BookRepository;

@Controller
public class WebController {

    @Autowired
    private BookRepository bookRepository;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "home";
    }

    @GetMapping("/admin/register")
    public String showRegistrationForm(Model model) {
        return "admin-register";
    }

    @GetMapping("/bookstore/home")
    public String bookstoreHome() {
        return "bookstore/home";
    }

    @GetMapping("/bookstore/login")
    public String bookstoreLogin() {
        return "bookstore-login";
    }
} 