package com.debish.health.patientrest.api.controller;

import com.debish.health.patientrest.api.request.CreatePatientRequest;
import com.debish.health.patientrest.api.request.UpdatePatientRequest;
import com.debish.health.patientrest.api.response.PatientResponse;
import com.debish.health.patientrest.application.dto.PatientCommand;
import com.debish.health.patientrest.application.service.PatientApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientApplicationService patientService;

    public PatientController(PatientApplicationService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody CreatePatientRequest request) {
        PatientResponse response = PatientResponse.from(patientService.create(toCommand(request)));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public List<PatientResponse> findAll() {
        return patientService.findAll().stream()
                .map(PatientResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public PatientResponse findById(@PathVariable Long id) {
        return PatientResponse.from(patientService.findById(id));
    }

    @PutMapping("/{id}")
    public PatientResponse update(@PathVariable Long id, @Valid @RequestBody UpdatePatientRequest request) {
        return PatientResponse.from(patientService.update(id, toCommand(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private PatientCommand toCommand(CreatePatientRequest request) {
        return new PatientCommand(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phone()
        );
    }

    private PatientCommand toCommand(UpdatePatientRequest request) {
        return new PatientCommand(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phone()
        );
    }
}
