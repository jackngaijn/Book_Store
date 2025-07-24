package com.example.demo.repository;

import com.example.demo.model.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
    Optional<Moderator> findByUsername(String username);
}