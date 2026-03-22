package com.debish.health.payment.application.event;

import java.math.BigDecimal;
import java.time.Instant;

public record ClaimAdjudicatedEvent(
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
            String adjudicationDecision,
            BigDecimal payableAmount,
            String currency,
            Instant adjudicatedAt
    ) {
    }
}
