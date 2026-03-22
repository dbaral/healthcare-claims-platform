package com.debish.health.notification.api.response;

import com.debish.health.notification.domain.model.NotificationRecord;

import java.time.Instant;

public record NotificationResponse(
        String claimId,
        String eventType,
        String channel,
        String message,
        String status,
        Instant sentAt
) {
    public static NotificationResponse from(NotificationRecord record) {
        return new NotificationResponse(
                record.getClaimId(),
                record.getEventType(),
                record.getChannel(),
                record.getMessage(),
                record.getStatus(),
                record.getSentAt()
        );
    }
}
