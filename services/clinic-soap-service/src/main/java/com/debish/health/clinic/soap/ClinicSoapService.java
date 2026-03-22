package com.debish.health.clinic.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://clinic.health.debish.com/")
public interface ClinicSoapService {

    @WebMethod
    String ping();

    @WebMethod
    Long registerPatient(String firstName, String lastName, String mrn, String dob, String phone);
}