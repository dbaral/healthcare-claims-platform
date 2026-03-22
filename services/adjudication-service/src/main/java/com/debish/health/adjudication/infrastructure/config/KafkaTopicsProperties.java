package com.debish.health.adjudication.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.topics")
public record KafkaTopicsProperties(
        String claimFraudCleared,
        String claimAdjudicated
) {
}
