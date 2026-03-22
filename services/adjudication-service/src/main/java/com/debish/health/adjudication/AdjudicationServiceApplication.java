package com.debish.health.adjudication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AdjudicationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdjudicationServiceApplication.class, args);
    }
}
