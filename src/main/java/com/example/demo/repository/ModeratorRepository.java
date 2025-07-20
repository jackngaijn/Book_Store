package com.example.demo.repository;

import com.example.demo.model.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
    Moderator findByUsername(String username);
}
