package com.hauhh.configurations;

import com.hauhh.models.Permission;
import com.hauhh.models.Role;
import com.hauhh.models.User;
import com.hauhh.repositories.PermissionRepository;
import com.hauhh.repositories.RoleRepository;
import com.hauhh.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return args -> {
            Permission permission = Permission.builder()
                    .name("READ_DATA")
                    .description("Read data from database")
                    .build();
            permissionRepository.save(permission);
            Set<Permission> permissions = new HashSet<>();
            permissions.add(permission);
            Role role = Role.builder()
                    .name("ADMIN")
                    .description("Admin role")
                    .permissions(permissions)
                    .build();
            roleRepository.save(role);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            if (userRepository.findByUsername("admin").isEmpty()) {
                User adminUser = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123"))
                        .roles(roles)
                        .build();
                userRepository.save(adminUser);
            }
        };
    }
}
