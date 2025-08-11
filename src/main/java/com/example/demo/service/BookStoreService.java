package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.BookDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Author;
import com.example.demo.model.Category;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.dto.AuthorDTO;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.model.Shelf;
import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.ShelfRepository;

@Service
public class BookStoreService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    // ######################################################### book start #########################################################
    // Create book
    @Transactional
    public ApiResponse createBook(BookDTO bookDTO) {
        // check if book exists, if not throw exception
        Optional<Book> bookOptional = bookRepository.findBySubject(bookDTO.getSubject());
        if (bookOptional.isPresent()) {
            return new ApiResponse(
                "Book already exists", 
                null
            );
        }
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
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Transactional
    public ApiResponse deleteAuthor(Long id) {
        // check if author exists, if not throw exception   
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.deleteById(id);
            return new ApiResponse("Author deleted successfully", null);
        }
        throw new ResourceNotFoundException("Author not found");
    }

    // Author get all books
    public ApiResponse getAllBooksByAuthorName(String authorName) {
        Author author = authorRepository.findByName(authorName)
            .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        List<BookDTO> books = author.getBooks()
            .stream()
            .map(BookDTO::toBookDTO)
            .collect(Collectors.toList());
        return new ApiResponse("Books fetched successfully", books);
    }
    // ######################################################### author end #########################################################

    // ######################################################### Category start #########################################################
    // Create category
    @Transactional
    public ApiResponse createCategory(CategoryDTO categoryDTO) {
        // check if category exists, if not throw exception
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryDTO.getName());
        if (categoryOptional.isPresent()) {
            return new ApiResponse("Category already exists", null);
        }
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);
        return new ApiResponse("Category created successfully", category);
    }   

    // Read all categories
    public ApiResponse getAllCategories() {
        List<CategoryDTO> categories = categoryRepository.findAll()
            .stream()
            .map(CategoryDTO::toCategoryDTO)
            .collect(Collectors.toList());
        return new ApiResponse("Categories fetched successfully", categories);
    }

    // Read category by id
    public ApiResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return new ApiResponse("Category fetched successfully", category);
    }

    // Update category
    @Transactional
    public ApiResponse updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);
        return new ApiResponse("Category updated successfully", category);
    }

    // Remove category
    @Transactional
    public ApiResponse deleteCategory(Long id) {
        // check if category exists, if not throw exception 
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.deleteById(id);
            return new ApiResponse(
                "Category deleted successfully", 
                null
            );
        }
        throw new ResourceNotFoundException("Category not found");
    }

    //Get all books by category name
    public ApiResponse getAllBooksByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        List<BookDTO> books = category.getBooks()
            .stream()
            .map(BookDTO::toBookDTO)
            .collect(Collectors.toList());
        return new ApiResponse("Books fetched successfully", books);
    }
    // ######################################################### Category end #########################################################

    // ######################################################### shelf start #########################################################
    // Add book to appuser's shelf
    @Transactional
    public ApiResponse addBookToAppUserShelf(Long appuserId, Long bookid) {
        AppUser appUser = appUserRepository.findById(appuserId)
            .orElseThrow(() -> new ResourceNotFoundException("AppUser not found"));
        Book book = bookRepository.findById(bookid)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        Shelf shelf = new Shelf();
        shelf.setBook(book);
        shelf.setAppUser(appUser);
        shelf.setLastAccessDate(null);
        shelf.setNumberOfAccesses(0);
        shelfRepository.save(shelf);
        return new ApiResponse("Book added to shelf successfully", shelf);
    }

    // Read all books in appuser's shelf
    public ApiResponse getAllBooksInAppUserShelf(Long appUserId) {
        Optional<AppUser> appUser = appUserRepository.findById(appUserId);
        if (appUser.isPresent()) {
            AppUser appUserEntity = appUser.get();
            List<Shelf> shelf = shelfRepository.findByAppUserId(appUserEntity.getId());
            List<BookDTO> books = shelf.stream()
                .map(Shelf::getBook)
                .map(BookDTO::toBookDTO)
                .collect(Collectors.toList());
            return new ApiResponse("Books fetched successfully", books);
        }
        throw new ResourceNotFoundException("AppUser not found");
    }
    // ######################################################### shelf end #########################################################
}