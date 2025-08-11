package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.AuthorDTO;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.service.BookStoreService;

@RestController
public class BookStoreController {
    @Autowired
    private BookStoreService bookStoreService;

    // Repositories should not be accessed directly from controllers. Use services instead.

    // ######################################################### book start #########################################################
    // Create Book
    @PostMapping("/bookstore/create-book")
    public ResponseEntity<ApiResponse> createBookWithCategory(@Valid @RequestBody BookDTO bookDTO) {
        ApiResponse response = bookStoreService.createBook(bookDTO);
        return ResponseEntity.ok(response);
    }

    // Read all books
    @GetMapping("/bookstore/books")
    public ResponseEntity<ApiResponse> getAllBooks() {
        ApiResponse response = bookStoreService.getAllBooks();
        return ResponseEntity.ok(response);
    }

    // Read book by id
    @GetMapping("/bookstore/books/{id}")
    public ResponseEntity<ApiResponse> getBookById(@PathVariable Long id) {
        ApiResponse response = bookStoreService.getBookById(id);
        return ResponseEntity.ok(response);
    }

    // Update Book
    @PutMapping("/bookstore/books/{id}")
    public ResponseEntity<ApiResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        ApiResponse response = bookStoreService.updateBook(id, bookDTO);
        return ResponseEntity.ok(response);
    }

    // Remove Book
    @DeleteMapping("/bookstore/books/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long id) {
        ApiResponse response = bookStoreService.deleteBook(id);
        return ResponseEntity.ok(response);
    }
    // ######################################################### book end #########################################################

    // ######################################################### author start #########################################################
    // Create author
    @PostMapping("/bookstore/create-author")
    public ResponseEntity<ApiResponse> createAuthor(@Valid @RequestBody AuthorDTO AuthorDTO) {
        ApiResponse response = bookStoreService.createAuthor(AuthorDTO);
        return ResponseEntity.ok(response);
    }

    // Read all authors
    @GetMapping("/bookstore/authors")
    public ResponseEntity<ApiResponse> getAllAuthors() {
        ApiResponse response = bookStoreService.getAllAuthors();
        return ResponseEntity.ok(response);
    }

    // Read author by id
    @GetMapping("/bookstore/authors/{id}")
    public ResponseEntity<ApiResponse> getAuthorById(@PathVariable Long id) {
        ApiResponse response = bookStoreService.getAuthorById(id);
        return ResponseEntity.ok(response);
    }

    // Update author
    @PutMapping("/bookstore/authors/{id}")
    public ResponseEntity<ApiResponse> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) {
        ApiResponse response = bookStoreService.updateAuthor(id, authorDTO);
        return ResponseEntity.ok(response);
    }

    // Remove author
    @DeleteMapping("/bookstore/authors/{id}")
    public ResponseEntity<ApiResponse> deleteAuthor(@PathVariable Long id) {
        ApiResponse response = bookStoreService.deleteAuthor(id);
        return ResponseEntity.ok(response);
    }

    // author get all books
    @GetMapping("/bookstore/authors/{authorName}/books")
    public ResponseEntity<ApiResponse> getAllBooksByAuthorName(@PathVariable String authorName) {
        ApiResponse response = bookStoreService.getAllBooksByAuthorName(authorName);
        return ResponseEntity.ok(response);
    }

    // ######################################################### author end #########################################################

    // ######################################################### appuser start #########################################################
    // get all appuser books
    @GetMapping("/bookstore/appuser-books/{appuserId}")
    public ResponseEntity<ApiResponse> getAllAppUserBooks(@PathVariable Long appuserId) {
        ApiResponse response = bookStoreService.getAllBooksInAppUserShelf(appuserId);
        return ResponseEntity.ok(response);
    }

    // add book to appuser shelf
    @PostMapping("/bookstore/appuser-addbook/{appuserId}/{bookid}")
    public ResponseEntity<ApiResponse> addBookToAppUserShelf(@PathVariable Long appuserId, @PathVariable Long bookid) {
        ApiResponse response = bookStoreService.addBookToAppUserShelf(appuserId, bookid);
        return ResponseEntity.ok(response);
    }
    // ######################################################### appuser end #########################################################

    // ######################################################### category start #########################################################
    // create category
    @PostMapping("/bookstore/create-category")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        ApiResponse response = bookStoreService.createCategory(categoryDTO);
        return ResponseEntity.ok(response);
    }

    // get all categories
    @GetMapping("/bookstore/categories")
    public ResponseEntity<ApiResponse> getAllCategories() {
        ApiResponse response = bookStoreService.getAllCategories();
        return ResponseEntity.ok(response);
    }

    // get category by id
    @GetMapping("/bookstore/categories/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        ApiResponse response = bookStoreService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }

    // update category
    @PutMapping("/bookstore/categories/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        ApiResponse response = bookStoreService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(response);
    }

    // delete category
    @DeleteMapping("/bookstore/categories/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        ApiResponse response = bookStoreService.deleteCategory(id);
        return ResponseEntity.ok(response);
    }

    // category get all books
    @GetMapping("/bookstore/categories/{categoryName}/books")
    public ResponseEntity<ApiResponse> getAllBooksByCategoryName(@PathVariable String categoryName) {
        ApiResponse response = bookStoreService.getAllBooksByCategoryName(categoryName);
        return ResponseEntity.ok(response);
    }
    // ######################################################### category end #########################################################
}
