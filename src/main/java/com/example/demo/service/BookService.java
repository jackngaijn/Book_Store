package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.Book;
import java.util.List;
import java.util.Optional;
import com.example.demo.repository.BookRepository;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.ApiResponse;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ApiResponse getAllBooks() {
        try {
            List<Book> books = bookRepository.findAll();
            
            if (books.isEmpty()) {
                ApiResponse response = new ApiResponse();
                response.setMessage("No books found");
                response.setData(null);
                return response;
            }
            
            List<BookDTO> bookDTOs = books.stream()
                .map(BookDTO::convertToDto)
                .collect(Collectors.toList());
                
            ApiResponse response = new ApiResponse();
            response.setMessage("Books fetched successfully");
            response.setData(bookDTOs);
            return response;
            
        } catch (Exception e) {
            ApiResponse response = new ApiResponse();
            response.setMessage("An error occurred while fetching books");
            response.setData(null);
            return response;
        }
    }

    public ApiResponse getBookById(Long id) {
        try {
            // Business validation
            if (id == null || id <= 0) {
                ApiResponse response = new ApiResponse();
                response.setMessage("Invalid book ID provided");
                response.setData(null);
                return response;
            }
            
            Optional<Book> bookOptional = bookRepository.findById(id);
            
            if (!bookOptional.isPresent()) {
                ApiResponse response = new ApiResponse();
                response.setMessage("Book not found");
                response.setData(null);
                return response;
            }
            
            BookDTO bookDTO = BookDTO.convertToDto(bookOptional.get());
            ApiResponse response = new ApiResponse();
            response.setMessage("Book fetched successfully");
            response.setData(bookDTO);
            return response;
            
        } catch (Exception e) {
            ApiResponse response = new ApiResponse();
            response.setMessage("An error occurred while fetching the book");
            response.setData(null);
            return response;
        }
    }
}
