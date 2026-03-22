package com.debish.health.fraudcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FraudCheckServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FraudCheckServiceApplication.class, args);
    }
}
