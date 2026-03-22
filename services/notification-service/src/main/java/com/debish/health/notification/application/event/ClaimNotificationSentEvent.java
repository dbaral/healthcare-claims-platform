package com.debish.health.notification.application.event;

import java.time.Instant;

public record ClaimNotificationSentEvent(
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
            String notificationStatus,
            String channel,
            Instant sentAt
    ) {
    }
}
