package com.debish.health.eligibility.application.service;

import com.debish.health.eligibility.application.event.ClaimValidatedEvent;
import com.debish.health.eligibility.application.event.EligibilityEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EligibilityProcessor {

    private final EligibilityEventPublisher eventPublisher;

    public EligibilityProcessor(EligibilityEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void process(ClaimValidatedEvent event) {
        String memberId = event.payload().memberId();
        boolean eligible = memberId != null
                && memberId.startsWith("MBR-")
                && !memberId.toUpperCase().contains("INACTIVE");

        if (eligible) {
            eventPublisher.publishEligible(event);
            return;
        }

        eventPublisher.publishIneligible(event, "Member plan is inactive or member identifier is not eligible");
    }
}
