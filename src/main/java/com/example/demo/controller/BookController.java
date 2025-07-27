package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.service.BookService;
import com.example.demo.dto.ApiResponse;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<ApiResponse> getAllBooks() {
        ApiResponse response = bookService.getAllBooks();
        
        // Controller maps business responses to HTTP status codes
        if (response.getMessage().contains("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } else if (response.getMessage().contains("No books found")) {
            return ResponseEntity.ok(response); // 200 with empty result is valid
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<ApiResponse> getBookById(@PathVariable Long id) {
        ApiResponse response = bookService.getBookById(id);
        
        // Controller maps business responses to HTTP status codes
        if (response.getMessage().contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else if (response.getMessage().contains("Invalid")) {
            return ResponseEntity.badRequest().body(response);
        } else if (response.getMessage().contains("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        
        return ResponseEntity.ok(response);
    }
}
