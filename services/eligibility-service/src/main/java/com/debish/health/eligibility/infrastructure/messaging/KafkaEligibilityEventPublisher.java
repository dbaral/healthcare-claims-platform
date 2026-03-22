package com.debish.health.eligibility.infrastructure.messaging;

import com.debish.health.eligibility.application.event.ClaimValidatedEvent;
import com.debish.health.eligibility.application.event.EligibilityEventPublisher;
import com.debish.health.eligibility.application.event.MemberEligibleEvent;
import com.debish.health.eligibility.application.event.MemberIneligibleEvent;
import com.debish.health.eligibility.infrastructure.config.KafkaTopicsProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class KafkaEligibilityEventPublisher implements EligibilityEventPublisher {

    private static final String EVENT_VERSION = "1.0";
    private static final String SOURCE = "eligibility-service";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topicsProperties;

    public KafkaEligibilityEventPublisher(KafkaTemplate<String, Object> kafkaTemplate,
                                          KafkaTopicsProperties topicsProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicsProperties = topicsProperties;
    }

    @Override
    public void publishEligible(ClaimValidatedEvent event) {
        MemberEligibleEvent eligibleEvent = new MemberEligibleEvent(
                UUID.randomUUID().toString(),
                "member-eligible",
                EVENT_VERSION,
                Instant.now(),
                event.traceId(),
                SOURCE,
                new MemberEligibleEvent.Payload(
                        event.payload().claimId(),
                        event.payload().memberId(),
                        event.payload().providerId(),
                        event.payload().facilityId(),
                        event.payload().claimType(),
                        event.payload().dateOfService(),
                        event.payload().totalClaimAmount(),
                        event.payload().currency(),
                        event.payload().patientResponsibility(),
                        "ACTIVE",
                        Instant.now()
                )
        );
        kafkaTemplate.send(topicsProperties.memberEligible(), event.payload().claimId(), eligibleEvent);
    }

    @Override
    public void publishIneligible(ClaimValidatedEvent event, String reason) {
        MemberIneligibleEvent ineligibleEvent = new MemberIneligibleEvent(
                UUID.randomUUID().toString(),
                "member-ineligible",
                EVENT_VERSION,
                Instant.now(),
                event.traceId(),
                SOURCE,
                new MemberIneligibleEvent.Payload(
                        event.payload().claimId(),
                        event.payload().memberId(),
                        event.payload().providerId(),
                        Instant.now(),
                        "INACTIVE",
                        List.of(reason)
                )
        );
        kafkaTemplate.send(topicsProperties.memberIneligible(), event.payload().claimId(), ineligibleEvent);
    }
}
