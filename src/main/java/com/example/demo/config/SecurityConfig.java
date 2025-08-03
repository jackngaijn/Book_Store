package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import com.example.demo.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {   
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/", "/hello", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/admin/register", "/appuser/register", "/test/admin").permitAll()
                .requestMatchers("/appuser/login", "/admin/login", "/logout", "/currentuser").permitAll()
                .requestMatchers("/bookstore/**").permitAll()
                // .requestMatchers("/user/**").hasRole("USER")
                // .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .formLogin((form) -> form
                .disable()
            )
            .logout((logout) -> logout
                .disable()                                                                                                                               
            )
            .userDetailsService(customUserDetailsService);
        
        return http.build();
    }
}