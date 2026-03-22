package com.debish.health.eligibility.application.event;

public interface EligibilityEventPublisher {

    void publishEligible(ClaimValidatedEvent event);

    void publishIneligible(ClaimValidatedEvent event, String reason);
}
