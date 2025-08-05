package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
// appuser_book
@Entity
@Table(name = "shelf", uniqueConstraints = @UniqueConstraint(columnNames = {"appuser_id", "book_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.time.LocalDateTime lastAccessDate;
    private int numberOfAccesses; 
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "appuser_id")
    private AppUser appUser;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "book_id")
    private Book book;

}
