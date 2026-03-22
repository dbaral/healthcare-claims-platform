package com.debish.health.eligibility.application.event;

import java.time.Instant;
import java.util.List;

public record MemberIneligibleEvent(
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
            Instant checkedAt,
            String planStatus,
            List<String> reasons
    ) {
    }
}
