package com.example.demo.dto;

import com.example.demo.model.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String subject;
    private String description;
    private String ISBN;
    private String content;
    private String content_type;
    private String authorName;
    private String publisher;
    private String category;
    private String created_date;
    private String price;

    public static BookDTO convertToDto(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setSubject(book.getSubject());
        dto.setDescription(book.getDescription());
        dto.setISBN(book.getISBN());
        dto.setContent(book.getContent());
        dto.setContent_type(book.getContent_type());
        dto.setAuthorName(book.getAuthorName());
        dto.setPublisher(book.getPublisher());
        dto.setCategory(book.getCategory());
        dto.setCreated_date(book.getCreated_date());
        dto.setPrice(book.getPrice());
        return dto;
    }
}