package com.debish.health.adjudication.application.event;

public interface ClaimAdjudicatedEventPublisher {

    void publish(ClaimFraudClearedEvent event, String decision, java.math.BigDecimal payableAmount);
}
