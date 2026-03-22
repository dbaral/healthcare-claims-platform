package com.debish.health.patientrest.application.service;

import com.debish.health.patientrest.application.dto.PatientCommand;
import com.debish.health.patientrest.application.event.PatientEventPublisher;
import com.debish.health.patientrest.domain.model.Patient;
import com.debish.health.patientrest.domain.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientApplicationServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientEventPublisher patientEventPublisher;

    @InjectMocks
    private PatientApplicationService patientApplicationService;

    @Test
    void shouldPublishCreatedEventWhenPatientIsCreated() {
        PatientCommand command = new PatientCommand("Ava", "Jordan", "ava.jordan@example.com", "+1 555 010 1111");
        Patient savedPatient = patientWithId(11L, command);

        when(patientRepository.existsByEmail(command.email())).thenReturn(false);
        when(patientRepository.save(org.mockito.ArgumentMatchers.any(Patient.class))).thenReturn(savedPatient);

        patientApplicationService.create(command);

        verify(patientEventPublisher).publish(argThat(matchesEvent("PATIENT_CREATED", 11L)));
    }

    @Test
    void shouldPublishDeletedEventWhenPatientIsDeleted() {
        PatientCommand command = new PatientCommand("Ava", "Jordan", "ava.jordan@example.com", "+1 555 010 1111");
        Patient patient = patientWithId(11L, command);

        when(patientRepository.findById(11L)).thenReturn(Optional.of(patient));

        patientApplicationService.delete(11L);

        verify(patientEventPublisher).publish(argThat(matchesEvent("PATIENT_DELETED", 11L)));
    }

    private static Patient patientWithId(Long id, PatientCommand command) {
        Patient patient = new Patient(command.firstName(), command.lastName(), command.email(), command.phone());

        try {
            Field idField = Patient.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(patient, id);
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Failed to set patient id for test", ex);
        }

        return patient;
    }

    private static ArgumentMatcher<com.debish.health.patientrest.application.event.PatientEvent> matchesEvent(String eventType,
                                                                                                                Long patientId) {
        return event -> event != null
                && eventType.equals(event.eventType())
                && patientId.equals(event.patientId());
    }
}
