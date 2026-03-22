package com.debish.health.claimintake.application.dto;

import com.debish.health.claimintake.api.request.CreateClaimRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ClaimSubmissionCommand(
        String memberId,
        String providerId,
        String facilityId,
        String claimType,
        LocalDate dateOfService,
        BigDecimal totalClaimAmount,
        String currency,
        BigDecimal patientResponsibility,
        String notes,
        List<DiagnosisCommand> diagnoses,
        List<ProcedureCommand> procedures
) {

    public static ClaimSubmissionCommand from(CreateClaimRequest request) {
        return new ClaimSubmissionCommand(
                request.memberId(),
                request.providerId(),
                request.facilityId(),
                request.claimType(),
                request.dateOfService(),
                request.totalClaimAmount(),
                request.currency(),
                request.patientResponsibility(),
                request.notes(),
                request.diagnoses().stream()
                        .map(diagnosis -> new DiagnosisCommand(diagnosis.code(), diagnosis.type()))
                        .toList(),
                request.procedures().stream()
                        .map(procedure -> new ProcedureCommand(procedure.code(), procedure.units(), procedure.amount()))
                        .toList()
        );
    }
}
