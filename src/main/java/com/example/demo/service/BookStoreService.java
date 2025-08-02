package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.BookDTO;
import com.example.demo.repository.BookRepository;

@Service
public class BookStoreService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> getAllBooks() {
        return bookRepository
            .findAll()
            .stream()
            .map(BookDTO::toBookDTO)
            .collect(Collectors.toList());
    }
}
