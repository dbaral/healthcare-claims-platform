package com.debish.health.claimvalidation.application.service;

import com.debish.health.claimvalidation.application.exception.ClaimValidationRecordNotFoundException;
import com.debish.health.claimvalidation.domain.model.ClaimValidationRecord;
import com.debish.health.claimvalidation.domain.repository.ClaimValidationRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClaimValidationQueryService {

    private final ClaimValidationRecordRepository repository;

    public ClaimValidationQueryService(ClaimValidationRecordRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ClaimValidationRecord> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ClaimValidationRecord findByClaimId(String claimId) {
        return repository.findByClaimId(claimId)
                .orElseThrow(() -> new ClaimValidationRecordNotFoundException(claimId));
    }
}
