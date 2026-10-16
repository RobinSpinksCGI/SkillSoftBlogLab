package com.codecademy.blog.session;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session != null) {

            session.removeAttribute("SPRING_SECURITY_CONTEXT");
            session.removeAttribute("draftBlog");
            // Optionally, remove other attributes related to authentication if necessary
        }
        // Also clear the SecurityContextHolder
        // This ensures that the authentication is cleared for the current thread
        // (Spring Security’s AnonymousAuthenticationFilter will set up a new anonymous context later)
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
    }
}
