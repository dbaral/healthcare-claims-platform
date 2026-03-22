package com.debish.health.adjudication.infrastructure.messaging;

import com.debish.health.adjudication.application.event.ClaimAdjudicatedEvent;
import com.debish.health.adjudication.application.event.ClaimAdjudicatedEventPublisher;
import com.debish.health.adjudication.application.event.ClaimFraudClearedEvent;
import com.debish.health.adjudication.infrastructure.config.KafkaTopicsProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Component
public class KafkaClaimAdjudicatedEventPublisher implements ClaimAdjudicatedEventPublisher {

    private static final String EVENT_VERSION = "1.0";
    private static final String SOURCE = "adjudication-service";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topicsProperties;

    public KafkaClaimAdjudicatedEventPublisher(KafkaTemplate<String, Object> kafkaTemplate,
                                               KafkaTopicsProperties topicsProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicsProperties = topicsProperties;
    }

    @Override
    public void publish(ClaimFraudClearedEvent event, String decision, BigDecimal payableAmount) {
        ClaimAdjudicatedEvent adjudicatedEvent = new ClaimAdjudicatedEvent(
                UUID.randomUUID().toString(),
                "claim-adjudicated",
                EVENT_VERSION,
                Instant.now(),
                event.traceId(),
                SOURCE,
                new ClaimAdjudicatedEvent.Payload(
                        event.payload().claimId(),
                        event.payload().memberId(),
                        event.payload().providerId(),
                        decision,
                        payableAmount,
                        event.payload().currency(),
                        Instant.now()
                )
        );
        kafkaTemplate.send(topicsProperties.claimAdjudicated(), event.payload().claimId(), adjudicatedEvent);
    }
}
