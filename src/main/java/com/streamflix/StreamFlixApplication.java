package com.streamflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * StreamFlix Application - A Hotstar/Netflix Clone
 * Main entry point for the Spring Boot application.
 *
 * @author StreamFlix Team
 * @version 1.0.0
 */
import com.streamflix.entity.User;
import com.streamflix.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class StreamFlixApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamFlixApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  StreamFlix is running!");
        System.out.println("  URL: http://localhost:8080");
        System.out.println("========================================\n");
    }

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Forcefully create or update admin user on startup
            User admin = userRepository.findByEmail("admin@streamflix.com").orElse(new User());
            admin.setName("Admin User");
            admin.setEmail("admin@streamflix.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("Admin account (admin@streamflix.com / admin123) verified.");
        };
    }
}
