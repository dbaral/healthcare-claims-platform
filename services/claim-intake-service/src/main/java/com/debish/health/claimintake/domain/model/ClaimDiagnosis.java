package com.debish.health.claimintake.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "claim_diagnoses")
public class ClaimDiagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id_fk", nullable = false)
    private Claim claim;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "diagnosis_type", nullable = false)
    private String type;

    public ClaimDiagnosis(String code, String type) {
        this.code = code;
        this.type = type;
    }
}
