package com.debish.health.claimvalidation.application.event;

import com.debish.health.claimvalidation.domain.model.ClaimValidationRecord;

public interface ClaimValidationEventPublisher {

    void publishValidated(ClaimValidationRecord record, String traceId);

    void publishFailed(ClaimValidationRecord record, String traceId);
}
