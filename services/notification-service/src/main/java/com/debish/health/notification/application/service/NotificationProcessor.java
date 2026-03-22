package com.debish.health.notification.application.service;

import com.debish.health.notification.domain.model.NotificationRecord;
import com.debish.health.notification.domain.repository.NotificationRecordRepository;
import com.debish.health.notification.infrastructure.messaging.NotificationEventPublisher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class NotificationProcessor {

    private final NotificationRecordRepository repository;
    private final NotificationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public NotificationProcessor(NotificationRecordRepository repository,
                                 NotificationEventPublisher eventPublisher,
                                 ObjectMapper objectMapper) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void process(String topic, String rawMessage) {
        try {
            JsonNode root = objectMapper.readTree(rawMessage);
            JsonNode payload = root.path("payload");
            String claimId = firstText(payload, "claimId");
            String traceId = firstText(root, "traceId");
            String eventType = firstText(root, "eventType");

            if ("claim-adjudicated".equals(eventType)
                    && !"DENIED".equals(payload.path("adjudicationDecision").asText())) {
                return;
            }

            NotificationRecord record = new NotificationRecord();
            record.setNotificationId("NTF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            record.setClaimId(claimId);
            record.setEventType(eventType.isBlank() ? topic : eventType);
            record.setChannel("INTERNAL");
            record.setMessage(buildMessage(eventType, payload));
            record.setStatus("SENT");
            record.setSentAt(Instant.now());

            NotificationRecord saved = repository.save(record);
            eventPublisher.publish(saved, traceId);
        } catch (Exception ignored) {
            // Keep notification non-blocking for the demo workflow.
        }
    }

    private String buildMessage(String eventType, JsonNode payload) {
        return switch (eventType) {
            case "payment-processed" -> "Payment processed for claim " + payload.path("claimId").asText();
            case "claim-validation-failed" -> "Claim validation failed for claim " + payload.path("claimId").asText();
            case "member-ineligible" -> "Member ineligible for claim " + payload.path("claimId").asText();
            case "claim-fraud-flagged" -> "Claim flagged for fraud review " + payload.path("claimId").asText();
            case "claim-adjudicated" -> "Claim denied during adjudication " + payload.path("claimId").asText();
            default -> "Notification generated for claim " + payload.path("claimId").asText();
        };
    }

    private String firstText(JsonNode node, String field) {
        JsonNode value = node.path(field);
        return value.isMissingNode() || value.isNull() ? "" : value.asText();
    }
}
