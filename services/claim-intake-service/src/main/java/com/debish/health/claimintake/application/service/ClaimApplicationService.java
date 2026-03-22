package com.debish.health.claimintake.application.service;

import com.debish.health.claimintake.application.dto.ClaimSubmissionCommand;
import com.debish.health.claimintake.application.event.ClaimSubmittedEventPublisher;
import com.debish.health.claimintake.application.exception.ClaimNotFoundException;
import com.debish.health.claimintake.domain.model.Claim;
import com.debish.health.claimintake.domain.model.ClaimDiagnosis;
import com.debish.health.claimintake.domain.model.ClaimProcedure;
import com.debish.health.claimintake.domain.model.ClaimStatus;
import com.debish.health.claimintake.domain.repository.ClaimRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ClaimApplicationService {

    private final ClaimRepository claimRepository;
    private final ClaimSubmittedEventPublisher eventPublisher;

    public ClaimApplicationService(ClaimRepository claimRepository,
                                   ClaimSubmittedEventPublisher eventPublisher) {
        this.claimRepository = claimRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Claim submit(ClaimSubmissionCommand command) {
        Claim claim = new Claim();
        claim.setClaimId("CLM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        claim.setMemberId(command.memberId());
        claim.setProviderId(command.providerId());
        claim.setFacilityId(command.facilityId());
        claim.setClaimType(command.claimType());
        claim.setDateOfService(command.dateOfService());
        claim.setTotalClaimAmount(command.totalClaimAmount());
        claim.setCurrency(command.currency());
        claim.setPatientResponsibility(command.patientResponsibility());
        claim.setNotes(command.notes());
        claim.setStatus(ClaimStatus.SUBMITTED);
        claim.setSubmittedAt(Instant.now());

        command.diagnoses().forEach(diagnosisCommand -> claim.addDiagnosis(
                new ClaimDiagnosis(diagnosisCommand.code(), diagnosisCommand.type())));
        command.procedures().forEach(procedureCommand -> claim.addProcedure(
                new ClaimProcedure(procedureCommand.code(), procedureCommand.units(), procedureCommand.amount())));

        Claim savedClaim = claimRepository.save(claim);
        eventPublisher.publish(savedClaim);
        return savedClaim;
    }

    @Transactional(readOnly = true)
    public List<Claim> findAll() {
        return claimRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Claim findByClaimId(String claimId) {
        return claimRepository.findByClaimId(claimId)
                .orElseThrow(() -> new ClaimNotFoundException(claimId));
    }
}
