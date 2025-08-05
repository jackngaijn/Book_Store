package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.BookDTO;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.AuthorRepository;

@Service
public class BookStoreService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<BookDTO> getAllBooks() {
        return bookRepository
            .findAll()
            .stream()
            .map(BookDTO::toBookDTO)
            .collect(Collectors.toList());
    }

    public Book createBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setSubject(bookDTO.getSubject());
        book.setDescription(bookDTO.getDescription());
        book.setIsbn(bookDTO.getIsbn());
        book.setContent(bookDTO.getContent());
        book.setContentType(bookDTO.getContentType());
        book.setPublisher(bookDTO.getPublisher());
        book.setCreatedDate(bookDTO.getCreatedDate());
        book.setPrice(bookDTO.getPrice());
        book.setCategory(categoryRepository.findByName(bookDTO.getCategory())
            .orElseThrow(() -> new RuntimeException("Category not found")));
        book.setAuthor(authorRepository.findByName(bookDTO.getAuthor())
            .orElseThrow(() -> new RuntimeException("Author not found")));
        return bookRepository.save(book);
    }   
}


