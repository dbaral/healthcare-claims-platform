package com.debish.health.payment.api.response;

import com.debish.health.payment.domain.model.PaymentRecord;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponse(
        String paymentId,
        String claimId,
        String memberId,
        String providerId,
        BigDecimal amount,
        String currency,
        String status,
        Instant processedAt
) {
    public static PaymentResponse from(PaymentRecord record) {
        return new PaymentResponse(
                record.getPaymentId(),
                record.getClaimId(),
                record.getMemberId(),
                record.getProviderId(),
                record.getAmount(),
                record.getCurrency(),
                record.getStatus(),
                record.getProcessedAt()
        );
    }
}
