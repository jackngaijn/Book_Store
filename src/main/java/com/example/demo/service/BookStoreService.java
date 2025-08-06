package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.BookDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Author;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.dto.AuthorDTO;

@Service
public class BookStoreService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    
    // ######################################################### book start #########################################################
    // Create book
    public ApiResponse createBook(BookDTO bookDTO) {
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
            .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        book.setAuthor(authorRepository.findByName(bookDTO.getAuthor())
            .orElseThrow(() -> new ResourceNotFoundException("Author not found")));
        bookRepository.save(book);

        return new ApiResponse(
            "Book created successfully", 
            book
        );
    }
        
    // Read all books
    public ApiResponse getAllBooks() {
        List<BookDTO> books = bookRepository
            .findAll()
            .stream()
            .map(BookDTO::toBookDTO)
            .collect(Collectors.toList());

        return new ApiResponse(
            "Books fetched successfully", 
            books
        );
    }

    // Read book by id
    public ApiResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return new ApiResponse("Book fetched successfully", book);
    }


    // Update Book
    public ApiResponse updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        book.setSubject(bookDTO.getSubject());
        book.setDescription(bookDTO.getDescription());
        book.setIsbn(bookDTO.getIsbn());
        book.setContent(bookDTO.getContent());
        book.setContentType(bookDTO.getContentType());
        book.setPublisher(bookDTO.getPublisher());
        book.setCreatedDate(bookDTO.getCreatedDate());
        book.setPrice(bookDTO.getPrice());
        book.setCategory(categoryRepository.findByName(bookDTO.getCategory())
        .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        book.setAuthor(authorRepository.findByName(bookDTO.getAuthor())
        .orElseThrow(() -> new ResourceNotFoundException("Author not found")));
        bookRepository.save(book);
        return new ApiResponse("Book updated successfully", book);
    }
        
    // Remove book
    public ApiResponse deleteBook(Long id) {
        
        // check if book exists, if not throw exception
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            return new ApiResponse("Book deleted successfully", null);
        }
        throw new ResourceNotFoundException("Book not found");
    }
    // ######################################################### book end #########################################################

    // ######################################################### author start #########################################################
    // Create author
    public ApiResponse createAuthor(AuthorDTO authorDTO) {
        // check if author exists, if not throw exception
        Optional<Author> authorOptional = authorRepository.findByName(authorDTO.getName());
        if (authorOptional.isPresent()) {
            throw new ResourceNotFoundException("Author already exists");
        }
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setEmail(authorDTO.getEmail());
        author.setCountry(authorDTO.getCountry());
        authorRepository.save(author);
        return new ApiResponse(
            "Author created successfully", 
            author
        );
    }

    // Read all authors
    public ApiResponse getAllAuthors() {
        List<AuthorDTO> authors = authorRepository.findAll()
            .stream()
            .map(AuthorDTO::toAuthorDTO)
            .collect(Collectors.toList());
        return new ApiResponse(
            "Authors fetched successfully", 
            authors
        );
    }

    // Read author by id
    public ApiResponse getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        return new ApiResponse("Author fetched successfully", author);
    }

    // Update author
    public ApiResponse updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        author.setName(authorDTO.getName());
        author.setEmail(authorDTO.getEmail());
        author.setCountry(authorDTO.getCountry());
        authorRepository.save(author);
        return new ApiResponse("Author updated successfully", author);
    }

    // Remove author
    public ApiResponse deleteAuthor(Long id) {
        // check if author exists, if not throw exception   
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.deleteById(id);
            return new ApiResponse("Author deleted successfully", null);
        }
        throw new ResourceNotFoundException("Author not found");
    }
    // ######################################################### author end #########################################################

}
        