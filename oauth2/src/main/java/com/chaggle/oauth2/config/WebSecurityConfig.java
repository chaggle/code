package com.chaggle.oauth2.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class WebSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager() {
        return null;
    }
}