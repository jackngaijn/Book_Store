package com.example.demo.service;
import com.example.demo.model.User;
import com.example.demo.model.Book;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public AuthenticationResult authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        
        if (user == null) {
            return new AuthenticationResult(false, "User not found.", null);
        }
        
        // Check if account is locked
        if (user.getNumberOfRetries() >= 5) {
            return new AuthenticationResult(false, "Account locked due to too many failed attempts.", user);
        }
        
        // Check password
        if (!password.equals(user.getPassword())) {
            int retries = user.getNumberOfRetries() + 1;
            user.setNumberOfRetries(retries);
            userRepository.save(user);
            
            int retriesLeft = 5 - retries;
            String errorMessage = retriesLeft > 0 
                ? "Invalid password. You have " + retriesLeft + " retries left."
                : "Account locked due to too many failed attempts.";
            
            return new AuthenticationResult(false, errorMessage, user);
        }
        
        // Password correct, check status
        if ("pending".equals(user.getStatus())) {
            user.setNumberOfRetries(0); // Optionally reset on pending
            userRepository.save(user);
            return new AuthenticationResult(false, "Your account is not approved yet. Please wait for approval.", user);
        }
        
        // Successful login
        user.setNumberOfRetries(0);
        userRepository.save(user);
        return new AuthenticationResult(true, "Login successful.", user);
    }
    
    public RegistrationResult registerUser(String username, String password, String name, 
                                         String email, String telephone, String mobile, String address) {
        // Check if username already exists
        if (userRepository.findByUsername(username) != null) {
            return new RegistrationResult(false, "Username already exists");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);
        user.setTelephone(telephone);
        user.setMobile(mobile);
        user.setAddress(address);
        user.setStatus("pending");
        user.setNumberOfRetries(0);
        user.setCreatedDate(LocalDateTime.now().toString());
        
        userRepository.save(user);
        
        return new RegistrationResult(true, "Registration successful. Please wait for approval.");
    }


    public Set<Book> getUserWithBooks(String username) {
        User user = userRepository.findByUsername(username);
        return user.getBooks();
    }
    
    public void addBookToUser(String username, Book book) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.getBooks().add(book);
            userRepository.save(user);
        }
    }
    
    
    // Result classes for better return value handling
    public static class AuthenticationResult {
        private final boolean success;
        private final String message;
        private final User user;
        
        public AuthenticationResult(boolean success, String message, User user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }
        
        public boolean isSuccess() { 
            return success; 
        }
        public String getMessage() { 
            return message; 
        }
        public User getUser() { 
            return user; 
        }
    }
    
    public static class RegistrationResult {
        private final boolean success;
        private final String message;
        
        public RegistrationResult(boolean success, String message) {
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
