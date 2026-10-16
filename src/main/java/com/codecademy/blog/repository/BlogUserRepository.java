package com.codecademy.blog.repository;

import com.codecademy.blog.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {
    Optional<BlogUser> findByUsername(String username);
}
