package com.debish.health.claimvalidation.domain.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "claim_validation_records")
public class ClaimValidationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "claim_id", nullable = false, unique = true, length = 32)
    private String claimId;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "facility_id")
    private String facilityId;

    @Column(name = "claim_type")
    private String claimType;

    @Column(name = "date_of_service")
    private LocalDate dateOfService;

    @Column(name = "total_claim_amount", precision = 12, scale = 2)
    private BigDecimal totalClaimAmount;

    @Column(name = "currency", length = 8)
    private String currency;

    @Column(name = "patient_responsibility", precision = 12, scale = 2)
    private BigDecimal patientResponsibility;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private ValidationStatus status;

    @Column(name = "processed_at", nullable = false)
    private Instant processedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "claim_validation_errors", joinColumns = @JoinColumn(name = "validation_record_id"))
    @Column(name = "error_message", nullable = false, length = 255)
    private List<String> errors = new ArrayList<>();
}
