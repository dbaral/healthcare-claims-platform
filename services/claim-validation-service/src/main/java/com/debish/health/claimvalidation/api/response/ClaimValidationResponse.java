package com.debish.health.claimvalidation.api.response;

import com.debish.health.claimvalidation.domain.model.ClaimValidationRecord;

import java.time.Instant;
import java.util.List;

public record ClaimValidationResponse(
        String claimId,
        String memberId,
        String providerId,
        String status,
        Instant processedAt,
        List<String> errors
) {

    public static ClaimValidationResponse from(ClaimValidationRecord record) {
        return new ClaimValidationResponse(
                record.getClaimId(),
                record.getMemberId(),
                record.getProviderId(),
                record.getStatus().name(),
                record.getProcessedAt(),
                record.getErrors()
        );
    }
}
