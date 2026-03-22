package com.debish.health.clinic.config;

import com.debish.health.clinic.soap.ClinicSoapServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

    @Bean
    public Endpoint clinicEndpoint(Bus bus, ClinicSoapServiceImpl impl) {
        EndpointImpl endpoint = new EndpointImpl(bus, impl);
        endpoint.publish("/ClinicService");
        return endpoint;
    }
}