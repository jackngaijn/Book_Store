package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.model.Author;
import com.example.demo.service.BookStoreService;
import com.example.demo.dto.BookDTO;
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

    // remove book
    @DeleteMapping("/bookstore/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    // update book
    @PutMapping("/bookstore/books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookRepository.save(book);
    }

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
    // assign category to book
    @PostMapping("/bookstore/create-book")
    public Book createBookWithCategory(@RequestBody BookDTO bookDTO) {
        return bookStoreService.createBook(bookDTO);
    }

    // get all books
    @GetMapping("/bookstore/books")
    public List<BookDTO> getAllBooks() {
        return bookStoreService.getAllBooks();
    }

    // get book by id
    @GetMapping("/bookstore/books/{id}")
    public Book getBookById(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        return book;
    }

    // ######################################################### book end #########################################################

    // ######################################################### author start #########################################################
    // create author
    @PostMapping("/bookstore/create-author")
    public Author createAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    // get all authors
    @GetMapping("/bookstore/authors")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // get author by id
    @GetMapping("/bookstore/authors/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    }

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
