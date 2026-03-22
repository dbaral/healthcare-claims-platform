package com.debish.health.patientrest.api.response;

import com.debish.health.patientrest.domain.model.Patient;

public record PatientResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone
) {

    public static PatientResponse from(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getPhone()
        );
    }
}
