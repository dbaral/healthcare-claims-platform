package com.debish.health.claimvalidation.application.exception;

public class ClaimValidationRecordNotFoundException extends RuntimeException {

    public ClaimValidationRecordNotFoundException(String claimId) {
        super("Validation record not found for claim: " + claimId);
    }
}
