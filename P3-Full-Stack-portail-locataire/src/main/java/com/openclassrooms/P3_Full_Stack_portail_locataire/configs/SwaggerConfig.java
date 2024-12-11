package com.openclassrooms.P3_Full_Stack_portail_locataire.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Rent Management")
                        .version("1.0")
                        .description("API for managing rentals"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")   // Le nom du schéma de sécurité
                                        .type(SecurityScheme.Type.HTTP)  // Type HTTP
                                        .scheme("bearer")  // Type de schéma HTTP : "bearer"
                                        .bearerFormat("JWT")  // Spécifie que le format du token est JWT
                        ));
    }
}
