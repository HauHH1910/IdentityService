package com.hauhh.config;

import com.hauhh.entity.User;
import com.hauhh.enums.Role;
import com.hauhh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                User adminUser = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123"))
                        .roles(roles)
                        .build();
                userRepository.save(adminUser);
                log.warn("Admin user has been create with default password 123");
            }
        };
    }

}
