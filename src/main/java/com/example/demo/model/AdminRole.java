package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "admin_role")
public class AdminRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // many to one relationship with Admin
    @ManyToOne
    @JoinColumn(name = "admin_id")
    @JsonIgnore  // Prevent circular reference back to Admin
    private Admin admin;

    // many to one relationship with Role
    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore  // Prevent circular reference back to Role
    private Role role;
}
