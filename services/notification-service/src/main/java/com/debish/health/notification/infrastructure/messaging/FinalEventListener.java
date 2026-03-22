package com.debish.health.notification.infrastructure.messaging;

import com.debish.health.notification.application.service.NotificationProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class FinalEventListener {

    private final NotificationProcessor processor;

    public FinalEventListener(NotificationProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(
            topics = {
                    "#{@kafkaTopicsProperties.paymentProcessed()}",
                    "#{@kafkaTopicsProperties.claimValidationFailed()}",
                    "#{@kafkaTopicsProperties.memberIneligible()}",
                    "#{@kafkaTopicsProperties.claimFraudFlagged()}",
                    "#{@kafkaTopicsProperties.claimAdjudicated()}"
            },
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "stringKafkaListenerContainerFactory"
    )
    public void onFinalEvent(String rawMessage, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        processor.process(topic, rawMessage);
    }
}
