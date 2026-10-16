package com.codecademy.blog.config;

import com.codecademy.blog.model.BlogUser;
import com.codecademy.blog.model.BlogPost;
import com.codecademy.blog.repository.BlogUserRepository;
import com.codecademy.blog.repository.BlogPostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(BlogUserRepository blogUserRepository, BlogPostRepository blogPostRepository) {

        return (args) -> {
            blogUserRepository.deleteAll();

            BlogUser john = new BlogUser("john123", "$2a$10$D7TRpBf22bbXSjQsL8CfBekJyJL3DPhzijYjKN4ajUBVKY8Lgn2oa");

            BlogPost post = new BlogPost(john, "Today’s reminder: take a break, laugh at something silly, and let the creativity flourish.", "Taking a Break");

            blogUserRepository.saveAll(List.of(john));
            blogPostRepository.saveAll(List.of(post));

            System.out.println("Database seeded.");
        };
    }
}