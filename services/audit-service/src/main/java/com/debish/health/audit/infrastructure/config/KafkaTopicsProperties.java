package com.debish.health.audit.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.topics")
public record KafkaTopicsProperties(
        String claimSubmitted,
        String claimValidated,
        String claimValidationFailed,
        String memberEligible,
        String memberIneligible,
        String claimFraudCleared,
        String claimFraudFlagged,
        String claimAdjudicated,
        String paymentProcessed,
        String claimNotificationSent
) {
}
