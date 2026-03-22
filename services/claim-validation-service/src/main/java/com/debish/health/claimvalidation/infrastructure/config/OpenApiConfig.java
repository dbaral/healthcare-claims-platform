package com.debish.health.claimvalidation.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI claimValidationOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Claim Validation Service API")
                .description("Consumes claim-submitted events, validates claims, stores results, and publishes outcomes.")
                .version("v1"));
    }
}
