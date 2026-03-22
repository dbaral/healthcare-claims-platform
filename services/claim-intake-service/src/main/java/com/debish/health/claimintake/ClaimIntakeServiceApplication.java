package com.debish.health.claimintake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ClaimIntakeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaimIntakeServiceApplication.class, args);
    }
}
