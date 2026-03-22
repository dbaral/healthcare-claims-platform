package com.debish.health.payment.domain.repository;

import com.debish.health.payment.domain.model.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {

    Optional<PaymentRecord> findByClaimId(String claimId);
}
