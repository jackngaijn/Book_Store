package com.example.demo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String subject;
    private String description;
    private String ISBN;
    private String content;
    private String content_type;
    private String authorName;
    private String publisher;
    private String category;
    private String created_date;
    private String price;

    //Many books to many users
    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private Set<AppUser> users = new HashSet<>();

    //Many books to one author
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
}