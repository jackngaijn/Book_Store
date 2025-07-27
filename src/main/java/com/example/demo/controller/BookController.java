package com.example.demo.controller;

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

    // Constructor injection instead of @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<ApiResponse> getAllBooks() {
        ResponseEntity<ApiResponse> response = bookService.getAllBooks();
        
        return response;
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<ApiResponse> getBookById(@PathVariable Long id) {
        ResponseEntity<ApiResponse> response = bookService.getBookById(id);
        
        return response;
    }
}
