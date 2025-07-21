package com.example.demo.controller;

import com.example.demo.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import com.example.demo.service.BookService.CreateBookResult;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    // Show list of books
    @GetMapping("/list")
    public String showBookList(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/user/login";
        }
        List<Book> books = bookService.getAllBooks();
        Set<Book> userBooks = userService.getUserWithBooks(session.getAttribute("user").toString());
        model.addAttribute("books", books);
        model.addAttribute("userBooks", userBooks);
        return "book/list";
    }

    // Show form to create a book
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/book/list";
        }
        return "book/create";
    }

    // Process form submission
    @PostMapping("/create")
    public String createBook(@RequestParam String subject,
                            @RequestParam String description,
                            @RequestParam String ISBN,
                            @RequestParam String content,
                            @RequestParam String content_type,
                            @RequestParam String authorName,
                            @RequestParam String publisher, 
                            @RequestParam String category, 
                            @RequestParam String price,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/user/login";
        }
        CreateBookResult result = bookService.createBook(subject, description, ISBN, content, content_type, authorName, publisher, category, price);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("success", result.getMessage());
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("error", result.getMessage());
            return "redirect:/author/create";
        }
    }

    // Add book to user's collection
    @PostMapping("/add")
    public String addBookToUser(@RequestParam Long bookId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/user/login";
        }
        
        String username = session.getAttribute("user").toString();
        Book book = bookService.getBookById(bookId);
        
        if (book != null) {
            userService.addBookToUser(username, book);
            redirectAttributes.addFlashAttribute("success", "Book added to your collection!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Book not found!");
        }
        
        return "redirect:/book/list";
    }
}