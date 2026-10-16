package com.codecademy.blog.repository;

import com.codecademy.blog.model.BlogPost;
import com.codecademy.blog.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByAuthorOrderByCreatedAtDesc(BlogUser author);
}
