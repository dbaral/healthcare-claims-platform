package com.debish.health.claimvalidation.domain.repository;

import com.debish.health.claimvalidation.domain.model.ClaimValidationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClaimValidationRecordRepository extends JpaRepository<ClaimValidationRecord, Long> {

    Optional<ClaimValidationRecord> findByClaimId(String claimId);
}
