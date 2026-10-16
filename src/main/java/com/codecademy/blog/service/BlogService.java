package com.codecademy.blog.service;


import com.codecademy.blog.model.BlogPost;
import com.codecademy.blog.model.BlogPostDto;
import com.codecademy.blog.model.BlogUser;
import com.codecademy.blog.repository.BlogPostRepository;
import com.codecademy.blog.repository.BlogUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    private final BlogPostRepository blogPostRepository;
    private final BlogUserRepository blogUserRepository;

    public BlogService(BlogPostRepository blogPostRepository, BlogUserRepository blogUserRepository) {
        this.blogPostRepository = blogPostRepository;
        this.blogUserRepository = blogUserRepository;
    }

    public BlogPost saveBlogPost(BlogPostDto newPost, String username) {
        BlogUser author = blogUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        BlogPost post = new BlogPost(author, newPost.getContent(), newPost.getTitle());
        return blogPostRepository.save(post);
    }

    public List<BlogPost> getUserPosts(String username) {
        BlogUser author = blogUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return blogPostRepository.findByAuthorOrderByCreatedAtDesc(author);
    }

    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }
}

