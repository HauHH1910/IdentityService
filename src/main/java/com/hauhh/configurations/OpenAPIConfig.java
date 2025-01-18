package com.hauhh.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI(@Value("${open.api.title}") String titleDocument,
                            @Value("${open.api.version}") String versionDocument,
                            @Value("${open.api.description}") String descriptionDocument,
                            @Value("${open.api.server.url}") String serverDocument,
                            @Value("${open.api.server.description}") String serverDescriptionDocument) {
        return new OpenAPI().info(new Info()
                .title(titleDocument)
                .version(versionDocument)
                .description(descriptionDocument)
                .license(new License()
                        .name("API License")
                        .url("https://github.com/HauHH1910/IdentityService"))
        ).servers(List.of(new Server()
                .url(serverDocument)
                .description(serverDescriptionDocument)
        )).components(
                new Components()
                        .addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
        ).security(List.of(new SecurityRequirement().addList("bearerAuth")));
    }

    @Bean
    public GroupedOpenApi groupedOpenAPI(){
        return GroupedOpenApi.builder()
                .group("API-Service")
                .packagesToScan("com.hauhh.controllers")
                .build();
    }

}
