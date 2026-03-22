package com.debish.health.notification.infrastructure.messaging;

import com.debish.health.notification.application.event.ClaimNotificationSentEvent;
import com.debish.health.notification.domain.model.NotificationRecord;
import com.debish.health.notification.infrastructure.config.KafkaTopicsProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class NotificationEventPublisher {

    private static final String EVENT_VERSION = "1.0";
    private static final String SOURCE = "notification-service";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topicsProperties;

    public NotificationEventPublisher(KafkaTemplate<String, Object> kafkaTemplate,
                                      KafkaTopicsProperties topicsProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicsProperties = topicsProperties;
    }

    public void publish(NotificationRecord record, String traceId) {
        ClaimNotificationSentEvent event = new ClaimNotificationSentEvent(
                UUID.randomUUID().toString(),
                "claim-notification-sent",
                EVENT_VERSION,
                Instant.now(),
                traceId,
                SOURCE,
                new ClaimNotificationSentEvent.Payload(
                        record.getClaimId(),
                        record.getStatus(),
                        record.getChannel(),
                        record.getSentAt()
                )
        );
        kafkaTemplate.send(topicsProperties.claimNotificationSent(), record.getClaimId(), event);
    }
}
