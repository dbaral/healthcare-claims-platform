package com.debish.health.claimintake.domain.repository;

import com.debish.health.claimintake.domain.model.Claim;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    @Override
    @EntityGraph(attributePaths = {"diagnoses", "procedures"})
    List<Claim> findAll();

    @EntityGraph(attributePaths = {"diagnoses", "procedures"})
    Optional<Claim> findByClaimId(String claimId);
}
