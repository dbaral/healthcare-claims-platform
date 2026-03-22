package com.debish.health.eligibility.application.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record ClaimValidatedEvent(
        String eventId,
        String eventType,
        String eventVersion,
        Instant eventTimestamp,
        String traceId,
        String source,
        Payload payload
) {
    public record Payload(
            String claimId,
            String memberId,
            String providerId,
            String facilityId,
            String claimType,
            LocalDate dateOfService,
            BigDecimal totalClaimAmount,
            String currency,
            BigDecimal patientResponsibility,
            Instant validatedAt,
            String validationStatus
    ) {
    }
}
