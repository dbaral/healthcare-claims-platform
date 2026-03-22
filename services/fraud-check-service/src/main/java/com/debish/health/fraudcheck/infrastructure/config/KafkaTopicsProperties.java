package com.debish.health.fraudcheck.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.topics")
public record KafkaTopicsProperties(
        String memberEligible,
        String claimFraudCleared,
        String claimFraudFlagged
) {
}
