package com.debish.health.claimintake.infrastructure.messaging;

import com.debish.health.claimintake.application.event.ClaimSubmittedEvent;
import com.debish.health.claimintake.application.event.ClaimSubmittedEventPublisher;
import com.debish.health.claimintake.domain.model.Claim;
import com.debish.health.claimintake.infrastructure.config.KafkaTopicsProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class KafkaClaimSubmittedEventPublisher implements ClaimSubmittedEventPublisher {

    private static final String EVENT_TYPE = "claim-submitted";
    private static final String EVENT_VERSION = "1.0";
    private static final String SOURCE = "claim-intake-service";

    private final KafkaTemplate<String, ClaimSubmittedEvent> kafkaTemplate;
    private final KafkaTopicsProperties topicsProperties;

    public KafkaClaimSubmittedEventPublisher(KafkaTemplate<String, ClaimSubmittedEvent> kafkaTemplate,
                                             KafkaTopicsProperties topicsProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicsProperties = topicsProperties;
    }

    @Override
    public void publish(Claim claim) {
        ClaimSubmittedEvent event = new ClaimSubmittedEvent(
                UUID.randomUUID().toString(),
                EVENT_TYPE,
                EVENT_VERSION,
                Instant.now(),
                UUID.randomUUID().toString(),
                SOURCE,
                new ClaimSubmittedEvent.Payload(
                        claim.getClaimId(),
                        claim.getMemberId(),
                        claim.getProviderId(),
                        claim.getFacilityId(),
                        claim.getClaimType(),
                        claim.getDateOfService(),
                        claim.getTotalClaimAmount(),
                        claim.getCurrency(),
                        claim.getPatientResponsibility(),
                        claim.getNotes(),
                        claim.getDiagnoses().stream()
                                .map(diagnosis -> new ClaimSubmittedEvent.DiagnosisPayload(
                                        diagnosis.getCode(),
                                        diagnosis.getType()))
                                .toList(),
                        claim.getProcedures().stream()
                                .map(procedure -> new ClaimSubmittedEvent.ProcedurePayload(
                                        procedure.getCode(),
                                        procedure.getUnits(),
                                        procedure.getAmount()))
                                .toList(),
                        claim.getSubmittedAt()
                )
        );

        kafkaTemplate.send(topicsProperties.claimSubmitted(), claim.getClaimId(), event);
    }
}
