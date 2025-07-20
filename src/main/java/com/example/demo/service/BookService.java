package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public CreateBookResult createBook(String subject, String description, String ISBN, String content, String content_type, String author, String publisher, String category, String price) {
        Book book = new Book();
        book.setSubject(subject);
        book.setDescription(description);
        book.setISBN(ISBN);
        book.setContent(content);
        book.setContentType(content_type);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);
        book.setCreatedDate(LocalDateTime.now().toString());
        book.setPrice(price);
        bookRepository.save(book);
        return new CreateBookResult(true, "Book created successfully.");
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public static class CreateBookResult {
        private final boolean success;
        private final String message;

        public CreateBookResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    
}
