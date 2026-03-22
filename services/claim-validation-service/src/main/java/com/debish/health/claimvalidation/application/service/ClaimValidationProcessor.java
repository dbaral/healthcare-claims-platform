package com.debish.health.claimvalidation.application.service;

import com.debish.health.claimvalidation.application.event.ClaimSubmittedEvent;
import com.debish.health.claimvalidation.application.event.ClaimValidationEventPublisher;
import com.debish.health.claimvalidation.domain.model.ClaimValidationRecord;
import com.debish.health.claimvalidation.domain.model.ValidationStatus;
import com.debish.health.claimvalidation.domain.repository.ClaimValidationRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClaimValidationProcessor {

    private final ClaimValidationRecordRepository repository;
    private final ClaimValidationEventPublisher eventPublisher;

    public ClaimValidationProcessor(ClaimValidationRecordRepository repository,
                                    ClaimValidationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void process(ClaimSubmittedEvent event) {
        List<String> errors = validate(event);
        ClaimValidationRecord record = repository.findByClaimId(event.payload().claimId())
                .orElseGet(ClaimValidationRecord::new);

        record.setClaimId(event.payload().claimId());
        record.setMemberId(event.payload().memberId());
        record.setProviderId(event.payload().providerId());
        record.setFacilityId(event.payload().facilityId());
        record.setClaimType(event.payload().claimType());
        record.setDateOfService(event.payload().dateOfService());
        record.setTotalClaimAmount(event.payload().totalClaimAmount());
        record.setCurrency(event.payload().currency());
        record.setPatientResponsibility(event.payload().patientResponsibility());
        record.setProcessedAt(Instant.now());
        record.setErrors(errors);

        if (errors.isEmpty()) {
            record.setStatus(ValidationStatus.VALIDATED);
            ClaimValidationRecord savedRecord = repository.save(record);
            eventPublisher.publishValidated(savedRecord, event.traceId());
            return;
        }

        record.setStatus(ValidationStatus.FAILED);
        ClaimValidationRecord savedRecord = repository.save(record);
        eventPublisher.publishFailed(savedRecord, event.traceId());
    }

    private List<String> validate(ClaimSubmittedEvent event) {
        List<String> errors = new ArrayList<>();

        if (event.payload().memberId() == null || event.payload().memberId().isBlank()
                || !event.payload().memberId().startsWith("MBR-")) {
            errors.add("Member ID must be present and start with MBR-");
        }

        if (event.payload().procedures() == null || event.payload().procedures().isEmpty()) {
            errors.add("At least one procedure code is required");
        } else if (event.payload().procedures().stream().anyMatch(procedure ->
                procedure.code() == null || procedure.code().isBlank())) {
            errors.add("Procedure code cannot be blank");
        }

        if (event.payload().diagnoses() == null || event.payload().diagnoses().isEmpty()) {
            errors.add("At least one diagnosis code is required");
        } else if (event.payload().diagnoses().stream().anyMatch(diagnosis ->
                diagnosis.code() == null || diagnosis.code().isBlank())) {
            errors.add("Diagnosis code cannot be blank");
        }

        if (event.payload().dateOfService() == null) {
            errors.add("Date of service is required");
        } else if (event.payload().dateOfService().isAfter(LocalDate.now())) {
            errors.add("Date of service cannot be in the future");
        }

        return errors;
    }
}
