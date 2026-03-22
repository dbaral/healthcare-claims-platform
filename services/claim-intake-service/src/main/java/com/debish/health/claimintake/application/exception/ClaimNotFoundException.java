package com.debish.health.claimintake.application.exception;

public class ClaimNotFoundException extends RuntimeException {

    public ClaimNotFoundException(String claimId) {
        super("Claim not found: " + claimId);
    }
}
