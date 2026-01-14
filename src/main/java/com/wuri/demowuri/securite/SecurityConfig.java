package com.wuri.demowuri.securite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
            "/api/v1/auth/**",
            "/api/v1/personnes/photo/{iu}",
            "/api/v1/documents/photo/{documentId}",
            "/api/v1/qrcodes/verify"
           

        ).permitAll()
        .requestMatchers("/error").permitAll()
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
         
        .requestMatchers("/api/v1/autorites/creer", "/api/v1/autorites/update/{id}", "/api/v1/autorites/delete/{id}","/api/v1/autorites/all")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/logs/date/{date}", "/api/v1/logs/current").hasRole("ADMIN")
        .requestMatchers("/api/v1/autorites/getById/{id}").hasAnyRole("ADMIN", "USER")
        .requestMatchers("/api/v1/roles/creer", "/api/v1/roles/update/{id}", "/api/v1/roles/delete/{id}")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/roles/show/{id}", "/api/v1/roles/liste").hasAnyRole("ADMIN", "USER")

        .requestMatchers("/api/v1/users/creer", "/api/v1/users/update/{id}",
            "/api/v1/users/delete/{id}","/api/v1/users/update/password/{id}")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/users/show/{id}", "api/v1/users/liste").hasAnyRole("ADMIN", "USER")
        .requestMatchers("/api/v1/documents/creer", "/api/v1/documents/update/{id}", "/api/v1/documents/delete/{id}","/api/v1/documents/autorite/{autoriteId}","/api/v1/documents/type/{typeId}","/api/v1/documents/all","/api/v1/documents/{documentId}/photo")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/documents/getById/{id}", "/api/v1/documents/search","/api/v1/documents/personnes/{id}/documents").hasAnyRole("ADMIN", "USER")

        .requestMatchers("/api/v1/eservices/creer", "/api/v1/eservices/update/{id}", "/api/v1/eservices/delete/{id}")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/eservices/getById/{id}", "/api/v1/eservices/all").hasAnyRole("ADMIN", "USER")

        .requestMatchers("/api/v1/notifications/creer", "/api/v1/notifications/all", "/api/v1/notifications/update/{id}", "/api/v1/notifications/delete/{id}")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/notifications/getById/{id}","/api/v1/notifications/personne/{personneId}","/api/v1/notifications/personne/{personneId}/unread").hasAnyRole("ADMIN", "USER")


        .requestMatchers("/api/v1/personnes/creer", "/api/v1/personnes/delete/{id}","/api/v1/personnes/all","/api/v1/personnes/{id}/activer","/api/v1/personnes/{id}/desactiver","/api/v1/personnes/{iu}/photo")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/personnes/getById/{id}","/api/v1/personnes/login","/api/v1/personnes/iu/{iu}","/api/v1/personnes/update/**").hasAnyRole("ADMIN", "USER")


        .requestMatchers( "/api/v1/qrcodes/update/{id}", "/api/v1/qrcodes/delete/{id}","/api/v1/qrcodes/all")
        .hasRole("ADMIN")
        .requestMatchers("/api/v1/qrcodes/creer","/api/v1/qrcodes/getById/{id}","/api/v1/qrcodes/personne/{personneId}","/api/v1/qrcodes/scan/{code}","/api/v1/qrcodes/personne/{personneId}/actifs").hasAnyRole("ADMIN", "USER")


        .requestMatchers("/api/v1/typedocuments/creer","/api/v1/typedocuments/getById/{id}", "/api/v1/typedocuments/all", "/api/v1/typedocuments/update/{id}", "/api/v1/typedocuments/delete/{id}")
        .hasRole("ADMIN")
      //  .requestMatchers().hasAnyRole("ADMIN", "USER")

        
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
