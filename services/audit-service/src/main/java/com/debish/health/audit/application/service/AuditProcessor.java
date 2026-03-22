package com.debish.health.audit.application.service;

import com.debish.health.audit.domain.model.AuditEventRecord;
import com.debish.health.audit.domain.repository.AuditEventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AuditProcessor {

    private final AuditEventRepository repository;
    private final ObjectMapper objectMapper;

    public AuditProcessor(AuditEventRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void record(String topic, String rawMessage) {
        try {
            JsonNode root = objectMapper.readTree(rawMessage);
            JsonNode payload = root.path("payload");

            AuditEventRecord record = new AuditEventRecord();
            record.setTopic(topic);
            record.setEventType(text(root, "eventType"));
            record.setTraceId(text(root, "traceId"));
            record.setClaimId(text(payload, "claimId"));
            record.setPayload(rawMessage);
            record.setRecordedAt(Instant.now());
            repository.save(record);
        } catch (Exception ignored) {
            // Audit should stay non-blocking for the demo project.
        }
    }

    private String text(JsonNode node, String field) {
        JsonNode value = node.path(field);
        return value.isMissingNode() || value.isNull() ? "" : value.asText();
    }
}
