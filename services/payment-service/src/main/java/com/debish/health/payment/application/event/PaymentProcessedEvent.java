package com.debish.health.payment.application.event;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentProcessedEvent(
        String eventId,
        String eventType,
        String eventVersion,
        Instant eventTimestamp,
        String traceId,
        String source,
        Payload payload
) {
    public record Payload(
            String paymentId,
            String claimId,
            String memberId,
            String providerId,
            BigDecimal amount,
            String currency,
            String paymentStatus,
            Instant processedAt
    ) {
    }
}
