package com.debish.health.patientrest.domain.repository;

import com.debish.health.patientrest.domain.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    Patient save(Patient patient);

    List<Patient> findAll();

    Optional<Patient> findById(Long id);

    Optional<Patient> findByEmail(String email);

    boolean existsByEmail(String email);

    void delete(Patient patient);
}
