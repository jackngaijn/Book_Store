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
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String subject;

    private String description;
    private String isbn;
    private String content;
    private String contentType;
    private String publisher;
    private java.util.Date createdDate;
    private double price;

    // #################### AppUserBook ######################
    // one to many relationship with user book
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AppUserBook> userBooks;
    // #################### AppUserBook ######################

    // #################### Author ######################
    // many to one relationship with author
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private Author author;
    // #################### Author ######################


    // #################### Category ######################
    // many to one relationship with category
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
    // #################### Category ######################
}
