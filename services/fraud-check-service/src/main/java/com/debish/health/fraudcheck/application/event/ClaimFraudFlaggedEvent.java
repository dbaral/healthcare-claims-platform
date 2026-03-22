package com.debish.health.fraudcheck.application.event;

import java.time.Instant;
import java.util.List;

public record ClaimFraudFlaggedEvent(
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
            Instant flaggedAt,
            List<String> reasons
    ) {
    }
}
