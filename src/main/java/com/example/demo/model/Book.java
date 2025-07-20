package com.example.demo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String ISBN;

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private String content_type;

    @Column(nullable = true)
    private String author;
    
    @Column(nullable = true)
    private String publisher;

    @Column(nullable = true)
    private String category;

    @Column(nullable = true)
    private String created_date;

    @Column(nullable = true)
    private String price;

    @ManyToMany(mappedBy = "books")
    private Set<User> users = new HashSet<>();

    public Book() {}

    public Book(String subject, String description, String ISBN, String content, String content_type, String author, String publisher, String category, String created_date, String price) {
        this.subject = subject;
        this.description = description;
        this.ISBN = ISBN;
        this.content = content;
        this.content_type = content_type;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.created_date = created_date;   
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return content_type;
    }

    public void setContentType(String content_type) {
        this.content_type = content_type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedDate() {
        return created_date;
    }

    public void setCreatedDate(String created_date) {
        this.created_date = created_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
