package com.debish.health.payment.application.exception;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(String claimId) {
        super("Payment not found for claim: " + claimId);
    }
}
