package com.example.demo.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "moderator")
public class Moderator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;
    
    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    // ManytoMany with Role
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "moderator_role",
        joinColumns = @JoinColumn(name = "moderator_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}