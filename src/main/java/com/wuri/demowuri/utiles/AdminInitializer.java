package com.wuri.demowuri.utiles;

import com.wuri.demowuri.model.Role;
import com.wuri.demowuri.model.User;
import com.wuri.demowuri.repository.RoleRepository;
import com.wuri.demowuri.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository,
                                       RoleRepository roleRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Vérifie si le rôle ROLE_ADMIN existe
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ADMIN");
                        return roleRepository.save(role);
                    });

            // 2. Vérifie si l'utilisateur admin existe
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRoles(Collections.singleton(adminRole));
                userRepository.save(admin);
                System.out.println("Utilisateur admin créé : admin / admin123");
            } else {
                System.out.println("L'utilisateur admin existe déjà.");
            }
        };
    }
}
