package com.debish.health.fraudcheck.application.event;

public interface FraudEventPublisher {

    void publishCleared(MemberEligibleEvent event);

    void publishFlagged(MemberEligibleEvent event, String reason);
}
