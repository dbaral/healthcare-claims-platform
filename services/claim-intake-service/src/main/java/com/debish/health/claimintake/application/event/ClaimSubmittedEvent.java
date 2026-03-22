package com.debish.health.claimintake.application.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record ClaimSubmittedEvent(
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
            String notes,
            List<DiagnosisPayload> diagnoses,
            List<ProcedurePayload> procedures,
            Instant submittedAt
    ) {
    }

    public record DiagnosisPayload(String code, String type) {
    }

    public record ProcedurePayload(String code, Integer units, BigDecimal amount) {
    }
}
