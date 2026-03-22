package com.debish.health.adjudication.infrastructure.messaging;

import com.debish.health.adjudication.application.event.ClaimFraudClearedEvent;
import com.debish.health.adjudication.application.service.AdjudicationProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ClaimFraudClearedListener {

    private final AdjudicationProcessor processor;

    public ClaimFraudClearedListener(AdjudicationProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(
            topics = "#{@kafkaTopicsProperties.claimFraudCleared()}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "claimFraudClearedKafkaListenerContainerFactory"
    )
    public void onClaimFraudCleared(ClaimFraudClearedEvent event) {
        processor.process(event);
    }
}
