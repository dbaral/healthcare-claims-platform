package com.debish.health.claimvalidation.infrastructure.web;

import com.debish.health.claimvalidation.application.exception.ClaimValidationRecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ClaimValidationRecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleRecordNotFound(ClaimValidationRecordNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        detail.setTitle("Validation Record Not Found");
        detail.setType(URI.create("https://healthcare-platform/errors/claim-validation-record-not-found"));
        return detail;
    }
}
