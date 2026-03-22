package com.debish.health.fraudcheck.application.service;

import com.debish.health.fraudcheck.application.event.FraudEventPublisher;
import com.debish.health.fraudcheck.application.event.MemberEligibleEvent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FraudCheckProcessor {

    private final FraudEventPublisher eventPublisher;

    public FraudCheckProcessor(FraudEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void process(MemberEligibleEvent event) {
        BigDecimal amount = event.payload().totalClaimAmount();
        boolean suspiciousAmount = amount != null && amount.compareTo(BigDecimal.valueOf(10000)) > 0;
        boolean suspiciousProvider = event.payload().providerId() != null
                && event.payload().providerId().toUpperCase().contains("RISK");

        if (suspiciousAmount || suspiciousProvider) {
            eventPublisher.publishFlagged(event, "Claim matched fraud threshold or suspicious provider pattern");
            return;
        }

        eventPublisher.publishCleared(event);
    }
}
