package com.debish.health.patientrest.infrastructure.messaging;

import com.debish.health.patientrest.application.event.PatientEvent;
import com.debish.health.patientrest.application.event.PatientEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPatientEventPublisher implements PatientEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaPatientEventPublisher.class);

    private final KafkaTemplate<String, PatientEvent> kafkaTemplate;
    private final KafkaTopicsProperties properties;

    public KafkaPatientEventPublisher(KafkaTemplate<String, PatientEvent> kafkaTemplate,
                                      KafkaTopicsProperties properties) {
        this.kafkaTemplate = kafkaTemplate;
        this.properties = properties;
    }

    @Override
    public void publish(PatientEvent event) {
        kafkaTemplate.send(properties.getTopic(), String.valueOf(event.patientId()), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish patient event {}", event.eventId(), ex);
                        return;
                    }

                    log.info("Published patient event {} to topic {} offset {}",
                            event.eventId(),
                            properties.getTopic(),
                            result.getRecordMetadata().offset());
                });
    }
}
