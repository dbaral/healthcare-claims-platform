package com.debish.health.patientrest.application.event;

public interface PatientEventPublisher {

    void publish(PatientEvent event);
}
