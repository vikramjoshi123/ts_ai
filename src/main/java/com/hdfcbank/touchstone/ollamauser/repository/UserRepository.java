package com.hdfcbank.touchstone.ollamauser.repository;

import com.hdfcbank.touchstone.ollamauser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
}
