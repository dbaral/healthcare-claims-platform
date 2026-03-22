package com.debish.health.patientrest.application.event;

import com.debish.health.patientrest.domain.model.Patient;

import java.time.Instant;
import java.util.UUID;

public record PatientEvent(
        String eventId,
        String eventType,
        String occurredAt,
        Long patientId,
        String firstName,
        String lastName,
        String email,
        String phone
) {

    public static PatientEvent created(Patient patient) {
        return from("PATIENT_CREATED", patient);
    }

    public static PatientEvent updated(Patient patient) {
        return from("PATIENT_UPDATED", patient);
    }

    public static PatientEvent deleted(Patient patient) {
        return from("PATIENT_DELETED", patient);
    }

    private static PatientEvent from(String eventType, Patient patient) {
        return new PatientEvent(
                UUID.randomUUID().toString(),
                eventType,
                Instant.now().toString(),
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getPhone()
        );
    }
}
