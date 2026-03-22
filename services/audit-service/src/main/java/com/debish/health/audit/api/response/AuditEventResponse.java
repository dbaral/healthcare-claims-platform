package com.debish.health.audit.api.response;

import com.debish.health.audit.domain.model.AuditEventRecord;

import java.time.Instant;

public record AuditEventResponse(
        String claimId,
        String eventType,
        String topic,
        Instant recordedAt,
        String traceId
) {
    public static AuditEventResponse from(AuditEventRecord record) {
        return new AuditEventResponse(
                record.getClaimId(),
                record.getEventType(),
                record.getTopic(),
                record.getRecordedAt(),
                record.getTraceId()
        );
    }
}
