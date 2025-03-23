package com.greensphere.userservice.service;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.entity.Role;
import com.greensphere.userservice.repository.RoleRepository;
import com.greensphere.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.beans.Transient;
import java.util.Optional;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class SuperAdminService implements CommandLineRunner {


        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) {
            createSuperAdmin();
        }

        @Bean
        @Order(1)
        @Transient
        public CommandLineRunner createSuperAdmin() {

            return args -> {
                String superAdminUsername = "superadmin";
                if (!userRepository.existsByUsername(superAdminUsername)) {

                    Role superAdminRole = roleRepository.findByName("ROLE_SUPER_ADMIN")
                            .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_SUPER_ADMIN").build()));

                    AppUser appUser = AppUser.builder()
                            .username(superAdminUsername)
                            .password(passwordEncoder.encode("ADMIN")) // Encode the password
                            .email("admin@example.com") // Provide a valid email
                            .status("ACTIVE")
                            .enabled(true) // Enable the user
                            .roles(Set.of(superAdminRole)) // Assign the role as a Set
                            .build();


                    userRepository.save(appUser);
                    System.out.println("Super admin user seeded!");
                }
            };

        }
}
