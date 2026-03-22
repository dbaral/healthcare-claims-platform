package com.debish.health.claimvalidation.application.event;

import java.time.Instant;
import java.util.List;

public record ClaimValidationFailedEvent(
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
            Instant failedAt,
            String validationStatus,
            List<String> errors
    ) {
    }
}
