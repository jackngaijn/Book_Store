package com.example.demo.model;

import jakarta.persistence.*;
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
@Table(name = "appuser")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String name;
    private String email;
    private String telephone;
    private String mobile;
    private String address;
    private String status;
    private String createdDate;
    private String updatedDate;
    private String approvedBy;
    private String approvedDate;
    private int numberOfRetries;
    private String lastLoginDate;

    // ManytoMany with Book
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "appuser_book",
        joinColumns = @JoinColumn(name = "appuser_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books;

    // ManytoMany with Role
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "appuser_role",
        joinColumns = @JoinColumn(name = "appuser_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}