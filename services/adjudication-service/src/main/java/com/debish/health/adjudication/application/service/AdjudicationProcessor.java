package com.debish.health.adjudication.application.service;

import com.debish.health.adjudication.application.event.ClaimAdjudicatedEventPublisher;
import com.debish.health.adjudication.application.event.ClaimFraudClearedEvent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AdjudicationProcessor {

    private final ClaimAdjudicatedEventPublisher eventPublisher;

    public AdjudicationProcessor(ClaimAdjudicatedEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void process(ClaimFraudClearedEvent event) {
        BigDecimal total = defaultAmount(event.payload().totalClaimAmount());
        BigDecimal responsibility = defaultAmount(event.payload().patientResponsibility());
        BigDecimal payableAmount = total.subtract(responsibility).max(BigDecimal.ZERO);

        String decision;
        if (payableAmount.compareTo(BigDecimal.ZERO) == 0) {
            decision = "DENIED";
        } else if (responsibility.compareTo(BigDecimal.ZERO) > 0) {
            decision = "PARTIAL";
        } else {
            decision = "APPROVED";
        }

        eventPublisher.publish(event, decision, payableAmount);
    }

    private BigDecimal defaultAmount(BigDecimal amount) {
        return amount == null ? BigDecimal.ZERO : amount;
    }
}
