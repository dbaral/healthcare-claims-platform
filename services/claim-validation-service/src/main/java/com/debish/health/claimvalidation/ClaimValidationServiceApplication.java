package com.debish.health.claimvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ClaimValidationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaimValidationServiceApplication.class, args);
    }
}
