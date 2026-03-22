package com.debish.health.patientrest.infrastructure.persistence;

import com.debish.health.patientrest.domain.model.Patient;
import com.debish.health.patientrest.domain.repository.PatientRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepositoryAdapter implements PatientRepository {

    private final SpringDataPatientRepository repository;

    public PatientRepositoryAdapter(SpringDataPatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Patient save(Patient patient) {
        return repository.save(patient);
    }

    @Override
    public List<Patient> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void delete(Patient patient) {
        repository.delete(patient);
    }
}
