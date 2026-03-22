package com.debish.health.fraudcheck.infrastructure.messaging;

import com.debish.health.fraudcheck.application.event.MemberEligibleEvent;
import com.debish.health.fraudcheck.application.service.FraudCheckProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MemberEligibleListener {

    private final FraudCheckProcessor processor;

    public MemberEligibleListener(FraudCheckProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(
            topics = "#{@kafkaTopicsProperties.memberEligible()}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "memberEligibleKafkaListenerContainerFactory"
    )
    public void onMemberEligible(MemberEligibleEvent event) {
        processor.process(event);
    }
}
