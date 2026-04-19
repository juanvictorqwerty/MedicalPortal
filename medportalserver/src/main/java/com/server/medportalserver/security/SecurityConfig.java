package com.server.medportalserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(
                        httpForm -> {
                            httpForm.loginPage("/login").permitAll();
                        })
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/login", "/register_supreme_admin", "/sign-up-supreme-admin",
                                    "/confirm-admin").permitAll();
                            auth.requestMatchers("/admin/**").hasRole("ADMIN");
                            auth.requestMatchers("/doctor/**").hasRole("DOCTOR");
                            auth.requestMatchers("/nurse/**").hasRole("NURSE");
                            auth.requestMatchers("/patient/**").hasRole("PATIENT");
                            auth.anyRequest().authenticated();
                        })
                .build();
    }
}
