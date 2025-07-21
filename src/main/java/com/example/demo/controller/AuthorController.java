package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;

@RequestMapping("/author")
@Controller
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/create")
    public String createAuthor() {
        return "author/create";
    }

    @PostMapping("/create")
    public String createAuthor(@RequestParam String name, 
                            @RequestParam String email, 
                            @RequestParam String country) {
        Author author = new Author(name, email, country);
        authorRepository.save(author);
        return "redirect:/author/create";
    }
}
