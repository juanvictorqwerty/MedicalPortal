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

                if (path.equals("/sign-up-supreme-admin") || path.equals("/confirm-admin")) {
                    String adminHeader = request.getHeader(adminHeaderName);
                    String registrationHeader = request.getHeader("X-Registration-Secret");

                    boolean adminValid = adminHeaderValue != null && adminHeaderValue.equals(adminHeader);
                    boolean registrationValid = registrationSecret != null && registrationSecret.equals(registrationHeader);

                    if (!adminValid || !registrationValid) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\": \"Missing or invalid security headers. Please ensure both 'X-Registration-Secret' and '" + adminHeaderName + "' are provided correctly.\"}");
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
                            auth.requestMatchers("/login", "/resend-token", "/sign-up-supreme-admin", "/confirm-admin").permitAll();

                            auth.requestMatchers("/admin/**").hasRole("ADMIN");
                            auth.requestMatchers("/doctor/**").hasRole("DOCTOR");
                            auth.requestMatchers("/nurse/**").hasRole("NURSE");
                            auth.requestMatchers("/patient/**").hasRole("PATIENT");
                            auth.anyRequest().authenticated();
                        })
                .build();
    }
}
