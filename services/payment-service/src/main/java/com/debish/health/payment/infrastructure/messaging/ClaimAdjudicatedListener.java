package com.debish.health.payment.infrastructure.messaging;

import com.debish.health.payment.application.event.ClaimAdjudicatedEvent;
import com.debish.health.payment.application.service.PaymentProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ClaimAdjudicatedListener {

    private final PaymentProcessor processor;

    public ClaimAdjudicatedListener(PaymentProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(
            topics = "#{@kafkaTopicsProperties.claimAdjudicated()}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "claimAdjudicatedKafkaListenerContainerFactory"
    )
    public void onClaimAdjudicated(ClaimAdjudicatedEvent event) {
        processor.process(event);
    }
}
