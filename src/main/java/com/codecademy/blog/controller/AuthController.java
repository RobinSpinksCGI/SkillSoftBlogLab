package com.codecademy.blog.controller;


import com.codecademy.blog.model.BlogUser;
import com.codecademy.blog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder encoder;
    public AuthController(AuthService authService, PasswordEncoder encoder) {
        this.authService = authService;
        this.encoder = encoder;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new BlogUser());
        return "auth";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new BlogUser());
        return "auth";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") BlogUser blogUser,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "auth";
        }

        if (authService.userExists(blogUser.getUsername())) {
            model.addAttribute("userExistsError", "Username already exists");
            return "auth";
        }

        authService.registerNewUser(blogUser);
        return "redirect:/auth/login?registered";
    }
}
