package com.debish.health.eligibility.infrastructure.messaging;

import com.debish.health.eligibility.application.event.ClaimValidatedEvent;
import com.debish.health.eligibility.application.service.EligibilityProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ClaimValidatedListener {

    private final EligibilityProcessor processor;

    public ClaimValidatedListener(EligibilityProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(
            topics = "#{@kafkaTopicsProperties.claimValidated()}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "claimValidatedKafkaListenerContainerFactory"
    )
    public void onClaimValidated(ClaimValidatedEvent event) {
        processor.process(event);
    }
}
