package com.debish.health.claimvalidation.api.controller;

import com.debish.health.claimvalidation.api.response.ClaimValidationResponse;
import com.debish.health.claimvalidation.application.service.ClaimValidationQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/claim-validations")
public class ClaimValidationQueryController {

    private final ClaimValidationQueryService queryService;

    public ClaimValidationQueryController(ClaimValidationQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public List<ClaimValidationResponse> findAll() {
        return queryService.findAll().stream()
                .map(ClaimValidationResponse::from)
                .toList();
    }

    @GetMapping("/{claimId}")
    public ClaimValidationResponse findByClaimId(@PathVariable String claimId) {
        return ClaimValidationResponse.from(queryService.findByClaimId(claimId));
    }
}
