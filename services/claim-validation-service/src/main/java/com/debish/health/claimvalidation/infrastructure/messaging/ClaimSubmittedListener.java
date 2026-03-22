package com.debish.health.claimvalidation.infrastructure.messaging;

import com.debish.health.claimvalidation.application.event.ClaimSubmittedEvent;
import com.debish.health.claimvalidation.application.service.ClaimValidationProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ClaimSubmittedListener {

    private final ClaimValidationProcessor processor;

    public ClaimSubmittedListener(ClaimValidationProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(
            topics = "#{@kafkaTopicsProperties.claimSubmitted()}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "claimSubmittedKafkaListenerContainerFactory"
    )
    public void onClaimSubmitted(ClaimSubmittedEvent event) {
        processor.process(event);
    }
}
