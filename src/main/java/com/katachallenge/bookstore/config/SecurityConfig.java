package com.katachallenge.bookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.HashSet;

/**
 * Configuration class for setting up Spring Security in the application.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Bean to provide the AuthenticationManager.
     * The AuthenticationManager is responsible for processing authentication requests.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserDetailsService userDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    /**
     * SecurityFilterChain defines the security filter chain for handling HTTP requests.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/customer/register",
                                "/api/customer/login",
                                "/h2-console/**",
                                "/api/books/getAllBooks"
                        ).permitAll() // Allow public access to these endpoints
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(frameOptions->frameOptions.disable()));

        // Return the configured SecurityFilterChain
        return http.build();
    }

    /**
     * Bean to provide a PasswordEncoder implementation.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean to provide a collection of authorities (roles/permissions) for a user.
     */
    @Bean
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }
}
