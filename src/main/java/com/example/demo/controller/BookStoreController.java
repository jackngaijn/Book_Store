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

@RestController
public class BookStoreController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

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
    public Book createBookWithCategory(@RequestBody Map<String, Object> bookData) {
        Book book = new Book();
        book.setSubject((String) bookData.get("subject"));
        book.setDescription((String) bookData.get("description"));
        book.setIsbn((String) bookData.get("isbn"));
        book.setContent((String) bookData.get("content"));
        book.setContentType((String) bookData.get("contentType"));
        book.setPublisher((String) bookData.get("publisher"));
        book.setCreatedDate(new java.util.Date());
        book.setPrice((Double) bookData.get("price"));

        Category category = categoryRepository.findByName((String) bookData.get("category"))
            .orElseThrow(() -> new RuntimeException("Category not found"));
        book.setCategory(category);

        Author author = authorRepository.findByName((String) bookData.get("author"))
            .orElseThrow(() -> new RuntimeException("Author not found"));
        book.setAuthor(author);

        return bookRepository.save(book);
    }

    // get all books
    @GetMapping("/bookstore/books")
    public List<Book> getAllBooks() {
        // get all books with category and author
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            book.setCategory(categoryRepository.findById(book.getCategory().getId()).orElseThrow(() -> new RuntimeException("Category not found")));
            book.setAuthor(authorRepository.findById(book.getAuthor().getId()).orElseThrow(() -> new RuntimeException("Author not found")));
        }
        return books;
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
}
