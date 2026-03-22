package com.debish.health.clinic.soap;

import com.debish.health.clinic.model.Patient;
import com.debish.health.clinic.service.PatientService;
import jakarta.jws.WebService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@WebService(
        serviceName = "ClinicService",
        portName = "ClinicServicePort",
        endpointInterface = "com.debish.health.clinic.soap.ClinicSoapService",
        targetNamespace = "http://clinic.health.debish.com/"
)
public class ClinicSoapServiceImpl implements ClinicSoapService {

    private final PatientService patientService;

    public ClinicSoapServiceImpl(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public String ping() {
        return "pong";
    }

    @Override
    public Long registerPatient(String firstName, String lastName, String mrn, String dob, String phone) {
        LocalDate date = (dob == null || dob.isBlank()) ? null : LocalDate.parse(dob); // yyyy-MM-dd
        Patient saved = patientService.registerPatient(firstName, lastName, mrn, date, phone);
        return saved.getId();
    }
}