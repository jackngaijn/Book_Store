package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "appuser")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String email;
    private boolean enabled = true;

    // One to many relationship with AppUserRole
    @JsonIgnore
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<AppUserRole> roles;

    // One to many relationship with AppUserBook
    @JsonIgnore
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<AppUserBook> books;
}