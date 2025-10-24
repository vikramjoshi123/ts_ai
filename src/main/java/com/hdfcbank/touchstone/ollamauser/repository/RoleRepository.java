package com.hdfcbank.touchstone.ollamauser.repository;

import com.hdfcbank.touchstone.ollamauser.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
