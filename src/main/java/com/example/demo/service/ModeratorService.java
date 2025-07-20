package com.example.demo.service;

import com.example.demo.model.Moderator;
import com.example.demo.model.User;
import com.example.demo.repository.ModeratorRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class ModeratorService {

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private UserRepository userRepository;

    public AuthenticationResult authenticateModerator(String username, String password) {
        Moderator moderator = moderatorRepository.findByUsername(username);
        if (moderator == null || !password.equals(moderator.getPassword())) {
            return new AuthenticationResult(false, "Invalid username or password", null);
        }
        moderator.setLastLoginDate(LocalDateTime.now().toString());
        moderatorRepository.save(moderator);
        return new AuthenticationResult(true, "Login successful", moderator);
    }

    public RegistrationResult registerModerator(String username, String password) {
        if (moderatorRepository.findByUsername(username) != null) {
            return new RegistrationResult(false, "Username already exists");
        } else {
            Moderator moderator = new Moderator();
            moderator.setUsername(username);
            moderator.setPassword(password);
            moderatorRepository.save(moderator);
            return new RegistrationResult(true, "Registration successful");
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void approveUser(String username, String moderator){
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setStatus("approved");
            user.setApprovedBy(moderator);
            user.setApprovedDate(LocalDateTime.now().toString());
            userRepository.save(user);
        }
    }

    public void lockUser(String username){
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setStatus("locked");
            userRepository.save(user);
        }
    }

    public void unlockUser(String username){
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setStatus("approved");
            userRepository.save(user);
        }
    }
    
    public void resetRetries(String username){
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setNumberOfRetries(0);
            user.setUpdatedDate(LocalDateTime.now().toString());
            userRepository.save(user);
        }
    }



    public static class AuthenticationResult {

        private final boolean success;
        private final String message;
        private final Moderator moderator;
        
        public AuthenticationResult(boolean success, String message, Moderator moderator) {
            this.success = success;
            this.message = message;
            this.moderator = moderator;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public Moderator getModerator() {
            return moderator;
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
