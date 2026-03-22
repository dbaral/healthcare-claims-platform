package com.debish.health.audit.api.controller;

import com.debish.health.audit.api.response.AuditEventResponse;
import com.debish.health.audit.domain.repository.AuditEventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit-events")
public class AuditQueryController {

    private final AuditEventRepository repository;

    public AuditQueryController(AuditEventRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AuditEventResponse> findAll() {
        return repository.findAll().stream().map(AuditEventResponse::from).toList();
    }
}
