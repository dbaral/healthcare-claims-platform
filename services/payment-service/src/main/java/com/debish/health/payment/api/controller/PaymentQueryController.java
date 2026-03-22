package com.debish.health.payment.api.controller;

import com.debish.health.payment.api.response.PaymentResponse;
import com.debish.health.payment.application.exception.PaymentNotFoundException;
import com.debish.health.payment.domain.repository.PaymentRecordRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentQueryController {

    private final PaymentRecordRepository repository;

    public PaymentQueryController(PaymentRecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PaymentResponse> findAll() {
        return repository.findAll().stream().map(PaymentResponse::from).toList();
    }

    @GetMapping("/{claimId}")
    public PaymentResponse findByClaimId(@PathVariable String claimId) {
        return repository.findByClaimId(claimId)
                .map(PaymentResponse::from)
                .orElseThrow(() -> new PaymentNotFoundException(claimId));
    }
}
