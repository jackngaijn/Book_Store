package com.example.demo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String created_date;

    @Column(nullable = true)
    private String updated_date;

    @Column(nullable = true)
    private String approved_by;

    @Column(nullable = true)
    private String approved_date;

    @Column(nullable = false)
    private int number_of_retries;

    @Column(nullable = true)
    private String last_login_date;

    @ManyToMany
    @JoinTable(
        name = "users_books",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )

    private Set<Book> books = new HashSet<>();

    public User() {}

    public User(String username, String password, String name, String email, String telephone, String mobile, String address, String status, int number_of_retries, String created_date) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.mobile = mobile;
        this.address = address;
        this.status = status;
        this.number_of_retries = number_of_retries;
        this.created_date = created_date;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return created_date;
    }
    public void setCreatedDate(String created_date) {
        this.created_date = created_date;
    }

    public String getUpdatedDate() {
        return updated_date;
    }
    public void setUpdatedDate(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getApprovedBy() {
        return approved_by;
    }
    public void setApprovedBy(String approved_by) {
        this.approved_by = approved_by;
    }

    public String getApprovedDate() {
        return approved_date;
    }
    public void setApprovedDate(String approved_date) {
        this.approved_date = approved_date;
    }

    public int getNumberOfRetries() {
        return number_of_retries;
    }
    public void setNumberOfRetries(int number_of_retries) {
        this.number_of_retries = number_of_retries;
    }

    public String getLastLoginDate() {
        return last_login_date;
    }
    public void setLastLoginDate(String last_login_date) {
        this.last_login_date = last_login_date;
    }

    public Set<Book> getBooks() {
        return books;
    }
    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}