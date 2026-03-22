package com.debish.health.claimvalidation.infrastructure.messaging;

import com.debish.health.claimvalidation.application.event.ClaimValidatedEvent;
import com.debish.health.claimvalidation.application.event.ClaimValidationEventPublisher;
import com.debish.health.claimvalidation.application.event.ClaimValidationFailedEvent;
import com.debish.health.claimvalidation.domain.model.ClaimValidationRecord;
import com.debish.health.claimvalidation.infrastructure.config.KafkaTopicsProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class KafkaClaimValidationEventPublisher implements ClaimValidationEventPublisher {

    private static final String EVENT_VERSION = "1.0";
    private static final String SOURCE = "claim-validation-service";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topicsProperties;

    public KafkaClaimValidationEventPublisher(KafkaTemplate<String, Object> kafkaTemplate,
                                              KafkaTopicsProperties topicsProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicsProperties = topicsProperties;
    }

    @Override
    public void publishValidated(ClaimValidationRecord record, String traceId) {
        ClaimValidatedEvent event = new ClaimValidatedEvent(
                UUID.randomUUID().toString(),
                "claim-validated",
                EVENT_VERSION,
                Instant.now(),
                traceId,
                SOURCE,
                new ClaimValidatedEvent.Payload(
                        record.getClaimId(),
                        record.getMemberId(),
                        record.getProviderId(),
                        record.getFacilityId(),
                        record.getClaimType(),
                        record.getDateOfService(),
                        record.getTotalClaimAmount(),
                        record.getCurrency(),
                        record.getPatientResponsibility(),
                        record.getProcessedAt(),
                        record.getStatus().name()
                )
        );
        kafkaTemplate.send(topicsProperties.claimValidated(), record.getClaimId(), event);
    }

    @Override
    public void publishFailed(ClaimValidationRecord record, String traceId) {
        ClaimValidationFailedEvent event = new ClaimValidationFailedEvent(
                UUID.randomUUID().toString(),
                "claim-validation-failed",
                EVENT_VERSION,
                Instant.now(),
                traceId,
                SOURCE,
                new ClaimValidationFailedEvent.Payload(
                        record.getClaimId(),
                        record.getMemberId(),
                        record.getProviderId(),
                        record.getProcessedAt(),
                        record.getStatus().name(),
                        record.getErrors()
                )
        );
        kafkaTemplate.send(topicsProperties.claimValidationFailed(), record.getClaimId(), event);
    }
}
