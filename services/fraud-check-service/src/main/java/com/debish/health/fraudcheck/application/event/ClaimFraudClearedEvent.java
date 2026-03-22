package com.debish.health.fraudcheck.application.event;

import java.math.BigDecimal;
import java.time.Instant;

public record ClaimFraudClearedEvent(
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
            BigDecimal totalClaimAmount,
            String currency,
            BigDecimal patientResponsibility,
            Instant clearedAt
    ) {
    }
}
