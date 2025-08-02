package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.AppUserRole;
import java.util.List;

public interface AppUserRoleRepository extends JpaRepository<AppUserRole, Long> {
    List<AppUserRole> findByAppUserId(Long userId);
} 