package com.debish.health.claimvalidation.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.topics")
public record KafkaTopicsProperties(
        String claimSubmitted,
        String claimValidated,
        String claimValidationFailed
) {
}
