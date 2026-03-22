package com.debish.health.patientrest.application.dto;

public record PatientCommand(
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
