package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.AdminRole;
import java.util.List;

public interface AdminRoleRepository extends JpaRepository<AdminRole, Long> {
    List<AdminRole> findByAdminId(Long adminId);
}
