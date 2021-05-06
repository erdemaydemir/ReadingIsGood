package com.erdem.readingisgood.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openApi = new OpenAPI();
        openApi.info(new Info().title(applicationName));
        openApi.components(
                new Components().addSecuritySchemes("bearer-jwt",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER).name("Authorization"))
        );
        openApi.addSecurityItem(new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")));
        return openApi;
    }

    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder().group("api").pathsToMatch("/api/**").build();
    }
}
