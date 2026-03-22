package com.debish.health.patientrest.infrastructure.messaging;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableConfigurationProperties(KafkaTopicsProperties.class)
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic patientEventsTopic(KafkaTopicsProperties properties) {
        return TopicBuilder.name(properties.getTopic())
                .partitions(3)
                .replicas(1)
                .build();
    }
}
