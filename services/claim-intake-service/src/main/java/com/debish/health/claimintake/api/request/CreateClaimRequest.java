package com.debish.health.claimintake.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CreateClaimRequest(
        @NotBlank String memberId,
        @NotBlank String providerId,
        String facilityId,
        @NotBlank String claimType,
        @NotNull LocalDate dateOfService,
        @NotNull @DecimalMin("0.0") @Digits(integer = 10, fraction = 2) BigDecimal totalClaimAmount,
        @NotBlank String currency,
        @NotNull @DecimalMin("0.0") @Digits(integer = 10, fraction = 2) BigDecimal patientResponsibility,
        String notes,
        @Valid @NotEmpty List<DiagnosisRequest> diagnoses,
        @Valid @NotEmpty List<ProcedureRequest> procedures
) {

    public record DiagnosisRequest(
            @NotBlank String code,
            @NotBlank String type
    ) {
    }

    public record ProcedureRequest(
            @NotBlank String code,
            @NotNull Integer units,
            @NotNull @DecimalMin("0.0") @Digits(integer = 10, fraction = 2) BigDecimal amount
    ) {
    }
}
