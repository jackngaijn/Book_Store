package com.example.demo.dto;

import com.example.demo.model.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String subject;
    private String description;
    private String isbn;
    private String content;
    private String contentType;
    private String publisher;
    private java.util.Date createdDate;
    private double price;
    private String category;
    private String author;

    public static BookDTO toBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setSubject(book.getSubject());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setContent(book.getContent());
        bookDTO.setContentType(book.getContentType());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setCreatedDate(book.getCreatedDate());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setCategory(book.getCategory().getName());
        bookDTO.setAuthor(book.getAuthor().getName());
        return bookDTO;
    }
}
