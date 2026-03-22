package com.debish.health.claimintake.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "claims")
public class Claim {

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

    @Column(name = "claim_type", nullable = false)
    private String claimType;

    @Column(name = "date_of_service", nullable = false)
    private LocalDate dateOfService;

    @Column(name = "total_claim_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalClaimAmount;

    @Column(name = "currency", nullable = false, length = 8)
    private String currency;

    @Column(name = "patient_responsibility", nullable = false, precision = 12, scale = 2)
    private BigDecimal patientResponsibility;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private ClaimStatus status;

    @Column(name = "submitted_at", nullable = false)
    private Instant submittedAt;

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ClaimDiagnosis> diagnoses = new ArrayList<>();

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ClaimProcedure> procedures = new ArrayList<>();

    public void addDiagnosis(ClaimDiagnosis diagnosis) {
        diagnosis.setClaim(this);
        diagnoses.add(diagnosis);
    }

    public void addProcedure(ClaimProcedure procedure) {
        procedure.setClaim(this);
        procedures.add(procedure);
    }
}
