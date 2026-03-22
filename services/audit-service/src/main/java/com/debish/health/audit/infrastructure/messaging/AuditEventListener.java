package com.debish.health.audit.infrastructure.messaging;

import com.debish.health.audit.application.service.AuditProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class AuditEventListener {

    private final AuditProcessor processor;

    public AuditEventListener(AuditProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(
            topics = {
                    "#{@kafkaTopicsProperties.claimSubmitted()}",
                    "#{@kafkaTopicsProperties.claimValidated()}",
                    "#{@kafkaTopicsProperties.claimValidationFailed()}",
                    "#{@kafkaTopicsProperties.memberEligible()}",
                    "#{@kafkaTopicsProperties.memberIneligible()}",
                    "#{@kafkaTopicsProperties.claimFraudCleared()}",
                    "#{@kafkaTopicsProperties.claimFraudFlagged()}",
                    "#{@kafkaTopicsProperties.claimAdjudicated()}",
                    "#{@kafkaTopicsProperties.paymentProcessed()}",
                    "#{@kafkaTopicsProperties.claimNotificationSent()}"
            },
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "stringKafkaListenerContainerFactory"
    )
    public void onEvent(String rawMessage, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        processor.record(topic, rawMessage);
    }
}
