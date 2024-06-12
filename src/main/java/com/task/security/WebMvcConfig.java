package com.task.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebMvcConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("I am also registering");

        return http.csrf(csrf -> csrf.disable())
                   .authorizeHttpRequests(auth -> auth
                       .requestMatchers("/auth/student/login").permitAll()
                       .requestMatchers("/tasks/**").authenticated())
                   .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                   .build();
    }
}
