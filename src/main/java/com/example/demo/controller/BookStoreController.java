package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import com.example.demo.repository.BookRepository;
import com.example.demo.model.Book;
import java.util.List;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.model.Author;
import com.example.demo.service.BookStoreService;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.AuthorDTO;
import com.example.demo.model.Shelf;
import com.example.demo.repository.ShelfRepository;
import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookStoreController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookStoreService bookStoreService;

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    // ######################################################### category start #########################################################
    // create category
    @PostMapping("/bookstore/create-category")
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    // get all categories
    @GetMapping("/bookstore/categories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // get category by id
    @GetMapping("/bookstore/categories/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // category get all books
    @GetMapping("/bookstore/categories/{categoryName}/books")
    public List<Book> getBooksByCategoryId(@PathVariable String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new RuntimeException("Category not found"));
        return category.getBooks();
    }

    // ######################################################### category end #########################################################

    // ######################################################### book start #########################################################
    // Create Book
    @PostMapping("/bookstore/create-book")
    public ResponseEntity<ApiResponse> createBookWithCategory(@RequestBody BookDTO bookDTO) {
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
    public ResponseEntity<ApiResponse> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
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
    public ResponseEntity<ApiResponse> createAuthor(@RequestBody AuthorDTO AuthorDTO) {
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
    public ResponseEntity<ApiResponse> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        ApiResponse response = bookStoreService.updateAuthor(id, authorDTO);
        return ResponseEntity.ok(response);
    }

    // Remove author
    @DeleteMapping("/bookstore/authors/{id}")
    public ResponseEntity<ApiResponse> deleteAuthor(@PathVariable Long id) {
        ApiResponse response = bookStoreService.deleteAuthor(id);
        return ResponseEntity.ok(response);
    }

    // ######################################################### author end #########################################################

    // author get all books
    @GetMapping("/bookstore/authors/{authorName}/books")
    public List<Book> getBooksByAuthorName(@PathVariable String authorName) {
        Author author = authorRepository.findByName(authorName).orElseThrow(() -> new RuntimeException("Author not found"));
        return author.getBooks();
    }

    // ######################################################### author end #########################################################

    // ######################################################### appuser start #########################################################
    // get all appuser books
    @GetMapping("/bookstore/appuser-books/{appuserId}")
    public List<Book> getAllAppUserBooks(@PathVariable Long appuserId) {
        Optional<AppUser> appUser = appUserRepository.findById(appuserId);
        if (appUser.isPresent()) {
            AppUser appUserEntity = appUser.get();

            List<Shelf> shelf = shelfRepository.findByAppUserId(appUserEntity.getId());
            List<Book> books = shelf.stream().map(Shelf::getBook).collect(Collectors.toList());
            return books;
        } else {
            throw new RuntimeException("AppUser not found");
        }
    }

    // add book to appuser shelf
    @PostMapping("/bookstore/appuser-addbook/{appuserId}/{bookid}")
    public Shelf addBookToAppUserShelf(@PathVariable Long appuserId, @PathVariable Long bookid) {
        AppUser appUser = appUserRepository.findById(appuserId).orElseThrow(() -> new RuntimeException("AppUser not found"));
        Book book = bookRepository.findById(bookid).orElseThrow(() -> new RuntimeException("Book not found"));
        Shelf shelf = new Shelf();
        shelf.setAppUser(appUser);
        shelf.setBook(book);
        shelf.setLastAccessDate(java.time.LocalDateTime.now());
        shelf.setNumberOfAccesses(0);
        return shelfRepository.save(shelf);
    }

    // ######################################################### appuser end #########################################################
}
