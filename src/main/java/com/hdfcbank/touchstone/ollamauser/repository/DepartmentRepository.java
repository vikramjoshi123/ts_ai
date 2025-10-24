package com.hdfcbank.touchstone.ollamauser.repository;

import com.hdfcbank.touchstone.ollamauser.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
}
