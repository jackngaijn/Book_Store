package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.ApiResponse;
import com.example.demo.service.BookService;
import com.example.demo.service.AppUserService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/bookstore")
public class BookStoreController {
    
    private final BookService bookService;
    private final AppUserService appUserService;

    public BookStoreController(BookService bookService, AppUserService appUserService) {
        this.bookService = bookService;
        this.appUserService = appUserService;
    }

    @GetMapping("/books")
    public ResponseEntity<ApiResponse> getAllBooks() {
        ApiResponse response = bookService.getAllBooks();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<ApiResponse> getBookById(@PathVariable Long id) {
        ApiResponse response = bookService.getBookById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        ApiResponse response = appUserService.getAllUsers();
        return ResponseEntity.ok(response);
    }
}
