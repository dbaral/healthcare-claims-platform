package com.debish.health.claimintake.api.controller;

import com.debish.health.claimintake.api.request.CreateClaimRequest;
import com.debish.health.claimintake.api.response.ClaimResponse;
import com.debish.health.claimintake.application.dto.ClaimSubmissionCommand;
import com.debish.health.claimintake.application.service.ClaimApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/claims")
public class ClaimIntakeController {

    private final ClaimApplicationService claimApplicationService;

    public ClaimIntakeController(ClaimApplicationService claimApplicationService) {
        this.claimApplicationService = claimApplicationService;
    }

    @PostMapping
    public ResponseEntity<ClaimResponse> submit(@Valid @RequestBody CreateClaimRequest request) {
        ClaimResponse response = ClaimResponse.from(claimApplicationService.submit(toCommand(request)));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{claimId}")
                .buildAndExpand(response.claimId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public List<ClaimResponse> findAll() {
        return claimApplicationService.findAll().stream()
                .map(ClaimResponse::from)
                .toList();
    }

    @GetMapping("/{claimId}")
    public ClaimResponse findByClaimId(@PathVariable String claimId) {
        return ClaimResponse.from(claimApplicationService.findByClaimId(claimId));
    }

    private ClaimSubmissionCommand toCommand(CreateClaimRequest request) {
        return ClaimSubmissionCommand.from(request);
    }
}
