package com.debish.health.clinic.service;

import com.debish.health.clinic.model.Patient;
import com.debish.health.clinic.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Patient registerPatient(String firstName, String lastName, String mrn,
                                   LocalDate dob, String phone) {

        patientRepository.findByMrn(mrn).ifPresent(p -> {
            throw new IllegalArgumentException("MRN already exists: " + mrn);
        });

        Patient patient = new Patient(firstName, lastName, mrn, dob, phone);
        return patientRepository.save(patient);
    }
}