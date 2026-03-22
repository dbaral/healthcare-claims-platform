package com.debish.health.patientrest.application.exception;

public class DuplicatePatientException extends RuntimeException {

    public DuplicatePatientException(String email) {
        super("A patient with email " + email + " already exists.");
    }
}
