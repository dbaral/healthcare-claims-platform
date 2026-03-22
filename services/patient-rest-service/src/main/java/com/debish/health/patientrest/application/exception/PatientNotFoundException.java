package com.debish.health.patientrest.application.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(Long patientId) {
        super("Patient with id " + patientId + " was not found.");
    }
}
