package com.wuri.demowuri.securite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers(
            "/api/auth/**"

        ).permitAll()
        .requestMatchers("/api/v1/produit/creer", "/api/v1/produit/update/{id}", "/api/v1/produit/delete/{id}")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/logs/date/{date}", "/api/v1/logs").hasRole("ADMIN")
        .requestMatchers("/api/v1/produit/show/{id}", "/api/v1/produits").hasAnyRole("ADMIN", "USER")
        .requestMatchers("/api/v1/roles/creer", "/api/v1/roles/update/{id}", "/api/v1/roles/delete/{id}")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/roles/show/{id}", "/api/v1/roles/liste").hasAnyRole("ADMIN", "USER")

        .requestMatchers("/api/v1/users/creer", "/api/v1/users/update/{id}",
            "/api/v1/users/delete/{id}")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/users/show/{id}", "api/v1/users/liste").hasAnyRole("ADMIN", "USER")
        .requestMatchers("/api/v1/categories/creer", "/api/v1/categories/update/{id}", "/api/v1/categories/delete/{id}")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/categories/show/{id}", "/api/v1/categories/liste").hasAnyRole("ADMIN", "USER")

        .anyRequest().authenticated());
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
