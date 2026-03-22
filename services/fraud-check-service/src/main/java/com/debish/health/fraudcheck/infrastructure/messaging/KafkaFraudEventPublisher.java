package com.debish.health.fraudcheck.infrastructure.messaging;

import com.debish.health.fraudcheck.application.event.ClaimFraudClearedEvent;
import com.debish.health.fraudcheck.application.event.ClaimFraudFlaggedEvent;
import com.debish.health.fraudcheck.application.event.FraudEventPublisher;
import com.debish.health.fraudcheck.application.event.MemberEligibleEvent;
import com.debish.health.fraudcheck.infrastructure.config.KafkaTopicsProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class KafkaFraudEventPublisher implements FraudEventPublisher {

    private static final String EVENT_VERSION = "1.0";
    private static final String SOURCE = "fraud-check-service";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topicsProperties;

    public KafkaFraudEventPublisher(KafkaTemplate<String, Object> kafkaTemplate,
                                    KafkaTopicsProperties topicsProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicsProperties = topicsProperties;
    }

    @Override
    public void publishCleared(MemberEligibleEvent event) {
        ClaimFraudClearedEvent clearedEvent = new ClaimFraudClearedEvent(
                UUID.randomUUID().toString(),
                "claim-fraud-cleared",
                EVENT_VERSION,
                Instant.now(),
                event.traceId(),
                SOURCE,
                new ClaimFraudClearedEvent.Payload(
                        event.payload().claimId(),
                        event.payload().memberId(),
                        event.payload().providerId(),
                        event.payload().facilityId(),
                        event.payload().claimType(),
                        event.payload().totalClaimAmount(),
                        event.payload().currency(),
                        event.payload().patientResponsibility(),
                        Instant.now()
                )
        );
        kafkaTemplate.send(topicsProperties.claimFraudCleared(), event.payload().claimId(), clearedEvent);
    }

    @Override
    public void publishFlagged(MemberEligibleEvent event, String reason) {
        ClaimFraudFlaggedEvent flaggedEvent = new ClaimFraudFlaggedEvent(
                UUID.randomUUID().toString(),
                "claim-fraud-flagged",
                EVENT_VERSION,
                Instant.now(),
                event.traceId(),
                SOURCE,
                new ClaimFraudFlaggedEvent.Payload(
                        event.payload().claimId(),
                        event.payload().memberId(),
                        event.payload().providerId(),
                        Instant.now(),
                        List.of(reason)
                )
        );
        kafkaTemplate.send(topicsProperties.claimFraudFlagged(), event.payload().claimId(), flaggedEvent);
    }
}
