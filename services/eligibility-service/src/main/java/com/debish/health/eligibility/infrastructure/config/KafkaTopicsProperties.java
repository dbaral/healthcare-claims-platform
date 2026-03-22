package com.debish.health.eligibility.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.topics")
public record KafkaTopicsProperties(
        String claimValidated,
        String memberEligible,
        String memberIneligible
) {
}
