package com.debish.health.payment.application.event;

import com.debish.health.payment.domain.model.PaymentRecord;

public interface PaymentProcessedEventPublisher {

    void publish(PaymentRecord record, String traceId);
}
