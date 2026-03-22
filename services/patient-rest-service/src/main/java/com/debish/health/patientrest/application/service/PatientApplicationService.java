package com.debish.health.patientrest.application.service;

import com.debish.health.patientrest.application.dto.PatientCommand;
import com.debish.health.patientrest.application.event.PatientEvent;
import com.debish.health.patientrest.application.event.PatientEventPublisher;
import com.debish.health.patientrest.application.exception.DuplicatePatientException;
import com.debish.health.patientrest.application.exception.PatientNotFoundException;
import com.debish.health.patientrest.domain.model.Patient;
import com.debish.health.patientrest.domain.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientApplicationService {

    private final PatientRepository patientRepository;
    private final PatientEventPublisher patientEventPublisher;

    public PatientApplicationService(PatientRepository patientRepository,
                                     PatientEventPublisher patientEventPublisher) {
        this.patientRepository = patientRepository;
        this.patientEventPublisher = patientEventPublisher;
    }

    public Patient create(PatientCommand command) {
        if (patientRepository.existsByEmail(command.email())) {
            throw new DuplicatePatientException(command.email());
        }

        Patient patient = new Patient(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.phone()
        );
        Patient savedPatient = patientRepository.save(patient);
        patientEventPublisher.publish(PatientEvent.created(savedPatient));
        return savedPatient;
    }

    @Transactional(readOnly = true)
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Patient findById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(patientId));
    }

    public Patient update(Long patientId, PatientCommand command) {
        Patient patient = findById(patientId);

        patientRepository.findByEmail(command.email())
                .filter(existing -> !existing.getId().equals(patientId))
                .ifPresent(existing -> {
                    throw new DuplicatePatientException(command.email());
                });

        patient.updateDetails(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.phone()
        );

        Patient savedPatient = patientRepository.save(patient);
        patientEventPublisher.publish(PatientEvent.updated(savedPatient));
        return savedPatient;
    }

    public void delete(Long patientId) {
        Patient patient = findById(patientId);
        patientRepository.delete(patient);
        patientEventPublisher.publish(PatientEvent.deleted(patient));
    }
}
