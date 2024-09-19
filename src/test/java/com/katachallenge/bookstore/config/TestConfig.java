package com.katachallenge.bookstore.config;

import com.katachallenge.bookstore.exceptions.UserNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;

@Configuration
public class TestConfig {

    @Bean
    public JwtUtil jwtUtil() {
        // Return a mock or dummy implementation
        return new JwtUtil(); // Ensure this is your actual class or a mock
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Return a mock or actual implementation
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
                // Return a mock user or implementation
                return new org.springframework.security.core.userdetails.User(username, "", new ArrayList<>());
            }
        };
    }
}