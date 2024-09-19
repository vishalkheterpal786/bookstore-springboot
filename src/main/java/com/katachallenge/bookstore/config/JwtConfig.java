package com.katachallenge.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    public JwtUtil getJwtUtil(){
        return new JwtUtil();
    }
}
