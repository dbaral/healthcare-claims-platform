package com.debish.health.claimintake.api.response;

import com.debish.health.claimintake.domain.model.Claim;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record ClaimResponse(
        String claimId,
        String memberId,
        String providerId,
        String facilityId,
        String claimType,
        String status,
        LocalDate dateOfService,
        BigDecimal totalClaimAmount,
        String currency,
        BigDecimal patientResponsibility,
        String notes,
        Instant submittedAt,
        List<DiagnosisResponse> diagnoses,
        List<ProcedureResponse> procedures
) {

    public static ClaimResponse from(Claim claim) {
        return new ClaimResponse(
                claim.getClaimId(),
                claim.getMemberId(),
                claim.getProviderId(),
                claim.getFacilityId(),
                claim.getClaimType(),
                claim.getStatus().name(),
                claim.getDateOfService(),
                claim.getTotalClaimAmount(),
                claim.getCurrency(),
                claim.getPatientResponsibility(),
                claim.getNotes(),
                claim.getSubmittedAt(),
                claim.getDiagnoses().stream()
                        .map(diagnosis -> new DiagnosisResponse(diagnosis.getCode(), diagnosis.getType()))
                        .toList(),
                claim.getProcedures().stream()
                        .map(procedure -> new ProcedureResponse(procedure.getCode(), procedure.getUnits(), procedure.getAmount()))
                        .toList()
        );
    }

    public record DiagnosisResponse(String code, String type) {
    }

    public record ProcedureResponse(String code, Integer units, BigDecimal amount) {
    }
}
