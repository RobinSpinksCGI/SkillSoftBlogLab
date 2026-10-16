package com.codecademy.blog.config;


import com.codecademy.blog.service.CustomUserDetailsService;
import com.codecademy.blog.session.CustomLogoutHandler;
import com.codecademy.blog.session.CustomSessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    public SecurityConfig() {}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomLogoutHandler customLogoutHandler) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/register").permitAll()
                .requestMatchers("/css/auth.css").permitAll()
                .requestMatchers("/css/blog.css").permitAll()
                .requestMatchers("/js/auth.js").permitAll()
                .anyRequest().authenticated()
        );

        http.formLogin(login -> login
                .defaultSuccessUrl("/blog")
                .failureUrl("/auth/login?error=true")
        );

        http.logout(logout -> logout
                .logoutUrl("/auth/logout")
                // used for codecademy workspace, ignore otherwise
                .addLogoutHandler(customLogoutHandler)
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    /**
     *
     *  Codecademy workspace beans below. Ignore
     */
    @Bean
    public FilterRegistrationBean<CustomSessionFilter> customSessionFilterRegistrationBean(CustomSessionFilter customSessionFilter) {
        FilterRegistrationBean<CustomSessionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(customSessionFilter);
        // Ensure this filter runs before Spring Security filters
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}