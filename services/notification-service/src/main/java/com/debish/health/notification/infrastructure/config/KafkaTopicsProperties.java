package com.debish.health.notification.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.topics")
public record KafkaTopicsProperties(
        String paymentProcessed,
        String claimValidationFailed,
        String memberIneligible,
        String claimFraudFlagged,
        String claimAdjudicated,
        String claimNotificationSent
) {
}
