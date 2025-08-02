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
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name; // e.g., ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR

    // One to many relationship with AppUserRole
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonIgnore  // Prevent circular reference to AppUserRole
    private List<AppUserRole> appUsers;

    // One to many relationship with AdminRole
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonIgnore  // Prevent circular reference to AdminRole
    private List<AdminRole> admins;

}