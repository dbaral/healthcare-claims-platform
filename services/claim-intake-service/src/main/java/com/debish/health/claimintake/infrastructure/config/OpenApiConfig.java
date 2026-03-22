package com.debish.health.claimintake.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI claimIntakeOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Claim Intake Service API")
                .description("Receives claims, stores the initial record, and publishes claim-submitted events.")
                .version("v1"));
    }
}
