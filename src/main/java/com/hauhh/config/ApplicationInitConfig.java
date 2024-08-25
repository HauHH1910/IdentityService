package com.hauhh.config;

import com.hauhh.entities.Permission;
import com.hauhh.entities.Role;
import com.hauhh.entities.User;
import com.hauhh.repository.PermissionRepository;
import com.hauhh.repository.RoleRepository;
import com.hauhh.repository.UserRepository;
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
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository){
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
            if(userRepository.findByUsername("admin").isEmpty()){
                User adminUser = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123"))
                        .roles(roles)
                        .build();
                userRepository.save(adminUser);
                log.warn("Admin user has been createPermission with default password 123");
            }
        };
    }

}
