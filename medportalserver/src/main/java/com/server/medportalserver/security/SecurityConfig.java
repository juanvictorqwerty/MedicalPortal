package com.server.medportalserver.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${registration.secret}")
    private String registrationSecret;

    @Value("${ADMIN_HEADER_NAME}")
    private String adminHeaderName;

    @Value("${ADMIN_HEADER_VALUE}")
    private String adminHeaderValue;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter adminHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                    HttpServletResponse response,
                    FilterChain filterChain)
                    throws ServletException, IOException {

                String path = request.getRequestURI();

                if (path.equals("/sign-up-supreme-admin")) {
                    String headerValue = request.getHeader(adminHeaderName);
                    if (adminHeaderValue == null || !adminHeaderValue.equals(headerValue)) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("Missing or invalid admin access header.");
                        return;
                    }
                }

                filterChain.doFilter(request, response);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(adminHeaderFilter(),
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .formLogin(
                        httpForm -> {
                            httpForm.loginPage("/login").permitAll();
                        })
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/login", "/resend-token").permitAll();

                            auth.requestMatchers("/sign-up-supreme-admin", "/confirm-admin")
                                    .access((authentication, context) -> {
                                        String clientSecret = context.getRequest().getHeader("X-Registration-Secret");
                                        boolean granted = registrationSecret != null
                                                && registrationSecret.equals(clientSecret);
                                        return new AuthorizationDecision(granted);
                                    });

                            auth.requestMatchers("/admin/**").hasRole("ADMIN");
                            auth.requestMatchers("/doctor/**").hasRole("DOCTOR");
                            auth.requestMatchers("/nurse/**").hasRole("NURSE");
                            auth.requestMatchers("/patient/**").hasRole("PATIENT");
                            auth.anyRequest().authenticated();
                        })
                .build();
    }
}
