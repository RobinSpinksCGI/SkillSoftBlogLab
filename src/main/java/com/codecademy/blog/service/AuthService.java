package com.codecademy.blog.service;


import com.codecademy.blog.model.BlogUser;
import com.codecademy.blog.repository.BlogUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private final BlogUserRepository blogUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthService(BlogUserRepository blogUserRepository) {
        this.blogUserRepository = blogUserRepository;
    }

    public BlogUser registerNewUser(BlogUser blogUser) {
        // Check if username already exists
        if (blogUserRepository.findByUsername(blogUser.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        blogUser.setPassword(this.passwordEncoder.encode(blogUser.getPassword()));

        return blogUserRepository.save(blogUser);
    }

    public boolean userExists(String username) {
        return blogUserRepository.findByUsername(username).isPresent();
    }
}