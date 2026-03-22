package com.debish.health.payment.infrastructure.messaging;

import com.debish.health.payment.application.event.PaymentProcessedEvent;
import com.debish.health.payment.application.event.PaymentProcessedEventPublisher;
import com.debish.health.payment.domain.model.PaymentRecord;
import com.debish.health.payment.infrastructure.config.KafkaTopicsProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class KafkaPaymentProcessedEventPublisher implements PaymentProcessedEventPublisher {

    private static final String EVENT_VERSION = "1.0";
    private static final String SOURCE = "payment-service";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topicsProperties;

    public KafkaPaymentProcessedEventPublisher(KafkaTemplate<String, Object> kafkaTemplate,
                                               KafkaTopicsProperties topicsProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicsProperties = topicsProperties;
    }

    @Override
    public void publish(PaymentRecord record, String traceId) {
        PaymentProcessedEvent event = new PaymentProcessedEvent(
                UUID.randomUUID().toString(),
                "payment-processed",
                EVENT_VERSION,
                Instant.now(),
                traceId,
                SOURCE,
                new PaymentProcessedEvent.Payload(
                        record.getPaymentId(),
                        record.getClaimId(),
                        record.getMemberId(),
                        record.getProviderId(),
                        record.getAmount(),
                        record.getCurrency(),
                        record.getStatus(),
                        record.getProcessedAt()
                )
        );
        kafkaTemplate.send(topicsProperties.paymentProcessed(), record.getClaimId(), event);
    }
}
