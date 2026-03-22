package com.debish.health.payment.application.service;

import com.debish.health.payment.application.event.ClaimAdjudicatedEvent;
import com.debish.health.payment.application.event.PaymentProcessedEventPublisher;
import com.debish.health.payment.domain.model.PaymentRecord;
import com.debish.health.payment.domain.repository.PaymentRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentProcessor {

    private final PaymentRecordRepository repository;
    private final PaymentProcessedEventPublisher eventPublisher;

    public PaymentProcessor(PaymentRecordRepository repository,
                            PaymentProcessedEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void process(ClaimAdjudicatedEvent event) {
        String decision = event.payload().adjudicationDecision();
        if (!"APPROVED".equals(decision) && !"PARTIAL".equals(decision)) {
            return;
        }

        PaymentRecord record = repository.findByClaimId(event.payload().claimId())
                .orElseGet(PaymentRecord::new);
        record.setPaymentId("PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        record.setClaimId(event.payload().claimId());
        record.setMemberId(event.payload().memberId());
        record.setProviderId(event.payload().providerId());
        record.setAmount(event.payload().payableAmount());
        record.setCurrency(event.payload().currency());
        record.setStatus("PROCESSED");
        record.setProcessedAt(Instant.now());

        PaymentRecord saved = repository.save(record);
        eventPublisher.publish(saved, event.traceId());
    }
}
