package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    List<Shelf> findByAppUserId(Long appUserId);
}
