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
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "appuser_book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "appuser_id")
    private AppUser appUser;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "book_id")
    private Book book;
}
