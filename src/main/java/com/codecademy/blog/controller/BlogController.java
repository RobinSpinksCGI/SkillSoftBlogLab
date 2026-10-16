package com.codecademy.blog.controller;


import com.codecademy.blog.model.BlogPostDto;
import com.codecademy.blog.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Collections;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public String blogHome(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        // Get blog posts for the current user
        model.addAttribute("posts", blogService.getUserPosts(username));
        // Check for draft in session
        BlogPostDto draft = null;
        if (draft == null) {
            draft = new BlogPostDto();
        }
        model.addAttribute("newPost", draft);

        // Add username for display
        model.addAttribute("username", username);

        return "blog";
    }

    @PostMapping("/save-draft")
    public String saveDraft(@ModelAttribute("newPost") BlogPostDto post) {
        return "redirect:/blog?draftSaved";
    }

    @PostMapping("/publish")
    public String publishPost(@Valid @ModelAttribute("newPost") BlogPostDto newPost,
                              BindingResult result,
                              Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        if (result.hasErrors()) {
            model.addAttribute("posts", blogService.getUserPosts(username));
            return "blog";
        }

        blogService.saveBlogPost(newPost, username);

        return "redirect:/blog?published";
    }
}
