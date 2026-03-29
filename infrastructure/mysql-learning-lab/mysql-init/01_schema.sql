USE healthcare_learning;

SET time_zone = '+00:00';

DROP VIEW IF EXISTS provider_payment_summary;
DROP VIEW IF EXISTS claim_lifecycle_summary;

DROP TABLE IF EXISTS audit_events;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS adjudications;
DROP TABLE IF EXISTS fraud_checks;
DROP TABLE IF EXISTS eligibility_checks;
DROP TABLE IF EXISTS claim_validation_errors;
DROP TABLE IF EXISTS claim_validations;
DROP TABLE IF EXISTS claim_procedures;
DROP TABLE IF EXISTS claim_diagnoses;
DROP TABLE IF EXISTS claims;
DROP TABLE IF EXISTS facilities;
DROP TABLE IF EXISTS providers;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS plans;

CREATE TABLE plans (
    plan_id VARCHAR(16) PRIMARY KEY,
    plan_name VARCHAR(120) NOT NULL,
    plan_type VARCHAR(40) NOT NULL,
    payer_name VARCHAR(120) NOT NULL,
    active TINYINT(1) NOT NULL,
    deductible_amount DECIMAL(12, 2) NOT NULL,
    out_of_pocket_max DECIMAL(12, 2) NOT NULL
);

CREATE TABLE patients (
    patient_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mrn VARCHAR(30) NOT NULL UNIQUE,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    date_of_birth DATE NOT NULL,
    sex VARCHAR(16) NOT NULL,
    phone VARCHAR(24),
    email VARCHAR(120) UNIQUE,
    city VARCHAR(80),
    state_code CHAR(2),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE members (
    member_id VARCHAR(32) PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    plan_id VARCHAR(16) NOT NULL,
    relationship_to_subscriber VARCHAR(32) NOT NULL,
    eligibility_status VARCHAR(16) NOT NULL,
    coverage_start_date DATE NOT NULL,
    coverage_end_date DATE NULL,
    CONSTRAINT fk_members_patient
        FOREIGN KEY (patient_id) REFERENCES patients (patient_id),
    CONSTRAINT fk_members_plan
        FOREIGN KEY (plan_id) REFERENCES plans (plan_id),
    INDEX idx_members_patient (patient_id),
    INDEX idx_members_plan (plan_id),
    INDEX idx_members_eligibility_status (eligibility_status)
);

CREATE TABLE providers (
    provider_id VARCHAR(32) PRIMARY KEY,
    provider_name VARCHAR(120) NOT NULL,
    specialty VARCHAR(80) NOT NULL,
    npi VARCHAR(20) NOT NULL UNIQUE,
    network_status VARCHAR(20) NOT NULL,
    city VARCHAR(80),
    state_code CHAR(2)
);

CREATE TABLE facilities (
    facility_id VARCHAR(32) PRIMARY KEY,
    facility_name VARCHAR(120) NOT NULL,
    facility_type VARCHAR(40) NOT NULL,
    city VARCHAR(80),
    state_code CHAR(2)
);

CREATE TABLE claims (
    claim_id VARCHAR(32) PRIMARY KEY,
    member_id VARCHAR(32) NOT NULL,
    provider_id VARCHAR(32) NOT NULL,
    facility_id VARCHAR(32) NULL,
    claim_type VARCHAR(32) NOT NULL,
    date_of_service DATE NOT NULL,
    submitted_at DATETIME NOT NULL,
    total_claim_amount DECIMAL(12, 2) NOT NULL,
    currency CHAR(3) NOT NULL,
    patient_responsibility DECIMAL(12, 2) NOT NULL,
    notes VARCHAR(1000),
    status VARCHAR(32) NOT NULL,
    CONSTRAINT fk_claims_member
        FOREIGN KEY (member_id) REFERENCES members (member_id),
    CONSTRAINT fk_claims_provider
        FOREIGN KEY (provider_id) REFERENCES providers (provider_id),
    CONSTRAINT fk_claims_facility
        FOREIGN KEY (facility_id) REFERENCES facilities (facility_id),
    INDEX idx_claims_member (member_id),
    INDEX idx_claims_provider (provider_id),
    INDEX idx_claims_facility (facility_id),
    INDEX idx_claims_status (status),
    INDEX idx_claims_service_date (date_of_service)
);

CREATE TABLE claim_diagnoses (
    diagnosis_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    claim_id VARCHAR(32) NOT NULL,
    diagnosis_code VARCHAR(20) NOT NULL,
    diagnosis_type VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT fk_claim_diagnoses_claim
        FOREIGN KEY (claim_id) REFERENCES claims (claim_id),
    INDEX idx_claim_diagnoses_claim (claim_id),
    INDEX idx_claim_diagnoses_code (diagnosis_code)
);

CREATE TABLE claim_procedures (
    procedure_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    claim_id VARCHAR(32) NOT NULL,
    procedure_code VARCHAR(20) NOT NULL,
    procedure_description VARCHAR(255),
    units INT NOT NULL,
    amount DECIMAL(12, 2) NOT NULL,
    CONSTRAINT fk_claim_procedures_claim
        FOREIGN KEY (claim_id) REFERENCES claims (claim_id),
    INDEX idx_claim_procedures_claim (claim_id),
    INDEX idx_claim_procedures_code (procedure_code)
);

CREATE TABLE claim_validations (
    validation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    claim_id VARCHAR(32) NOT NULL UNIQUE,
    member_id VARCHAR(32) NOT NULL,
    provider_id VARCHAR(32) NOT NULL,
    facility_id VARCHAR(32) NULL,
    claim_type VARCHAR(32),
    date_of_service DATE,
    total_claim_amount DECIMAL(12, 2),
    currency CHAR(3),
    patient_responsibility DECIMAL(12, 2),
    status VARCHAR(32) NOT NULL,
    processed_at DATETIME NOT NULL,
    CONSTRAINT fk_claim_validations_claim
        FOREIGN KEY (claim_id) REFERENCES claims (claim_id),
    CONSTRAINT fk_claim_validations_member
        FOREIGN KEY (member_id) REFERENCES members (member_id),
    CONSTRAINT fk_claim_validations_provider
        FOREIGN KEY (provider_id) REFERENCES providers (provider_id),
    CONSTRAINT fk_claim_validations_facility
        FOREIGN KEY (facility_id) REFERENCES facilities (facility_id),
    INDEX idx_claim_validations_status (status),
    INDEX idx_claim_validations_processed_at (processed_at)
);

CREATE TABLE claim_validation_errors (
    validation_error_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    validation_id BIGINT NOT NULL,
    error_message VARCHAR(255) NOT NULL,
    CONSTRAINT fk_claim_validation_errors_validation
        FOREIGN KEY (validation_id) REFERENCES claim_validations (validation_id),
    INDEX idx_claim_validation_errors_validation (validation_id)
);

CREATE TABLE eligibility_checks (
    eligibility_check_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    claim_id VARCHAR(32) NOT NULL UNIQUE,
    member_id VARCHAR(32) NOT NULL,
    plan_id VARCHAR(16) NOT NULL,
    status VARCHAR(32) NOT NULL,
    reason VARCHAR(255),
    checked_at DATETIME NOT NULL,
    CONSTRAINT fk_eligibility_checks_claim
        FOREIGN KEY (claim_id) REFERENCES claims (claim_id),
    CONSTRAINT fk_eligibility_checks_member
        FOREIGN KEY (member_id) REFERENCES members (member_id),
    CONSTRAINT fk_eligibility_checks_plan
        FOREIGN KEY (plan_id) REFERENCES plans (plan_id),
    INDEX idx_eligibility_checks_status (status)
);

CREATE TABLE fraud_checks (
    fraud_check_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    claim_id VARCHAR(32) NOT NULL UNIQUE,
    provider_id VARCHAR(32) NOT NULL,
    status VARCHAR(32) NOT NULL,
    rule_triggered VARCHAR(255),
    reviewed_by VARCHAR(80),
    reviewed_at DATETIME NULL,
    checked_at DATETIME NOT NULL,
    CONSTRAINT fk_fraud_checks_claim
        FOREIGN KEY (claim_id) REFERENCES claims (claim_id),
    CONSTRAINT fk_fraud_checks_provider
        FOREIGN KEY (provider_id) REFERENCES providers (provider_id),
    INDEX idx_fraud_checks_status (status)
);

CREATE TABLE adjudications (
    adjudication_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    claim_id VARCHAR(32) NOT NULL UNIQUE,
    decision VARCHAR(32) NOT NULL,
    total_claim_amount DECIMAL(12, 2) NOT NULL,
    patient_responsibility DECIMAL(12, 2) NOT NULL,
    payable_amount DECIMAL(12, 2) NOT NULL,
    denial_reason VARCHAR(255),
    processed_at DATETIME NOT NULL,
    CONSTRAINT fk_adjudications_claim
        FOREIGN KEY (claim_id) REFERENCES claims (claim_id),
    INDEX idx_adjudications_decision (decision),
    INDEX idx_adjudications_processed_at (processed_at)
);

CREATE TABLE payments (
    payment_id VARCHAR(32) PRIMARY KEY,
    claim_id VARCHAR(32) NOT NULL UNIQUE,
    member_id VARCHAR(32) NOT NULL,
    provider_id VARCHAR(32) NOT NULL,
    amount DECIMAL(12, 2) NOT NULL,
    currency CHAR(3) NOT NULL,
    status VARCHAR(32) NOT NULL,
    payment_method VARCHAR(40) NOT NULL,
    reference_number VARCHAR(40) NOT NULL UNIQUE,
    processed_at DATETIME NOT NULL,
    CONSTRAINT fk_payments_claim
        FOREIGN KEY (claim_id) REFERENCES claims (claim_id),
    CONSTRAINT fk_payments_member
        FOREIGN KEY (member_id) REFERENCES members (member_id),
    CONSTRAINT fk_payments_provider
        FOREIGN KEY (provider_id) REFERENCES providers (provider_id),
    INDEX idx_payments_status (status),
    INDEX idx_payments_processed_at (processed_at)
);

CREATE TABLE notifications (
    notification_id VARCHAR(32) PRIMARY KEY,
    claim_id VARCHAR(32) NOT NULL,
    event_type VARCHAR(40) NOT NULL,
    channel VARCHAR(32) NOT NULL,
    recipient VARCHAR(120) NOT NULL,
    message VARCHAR(500) NOT NULL,
    status VARCHAR(32) NOT NULL,
    sent_at DATETIME NOT NULL,
    CONSTRAINT fk_notifications_claim
        FOREIGN KEY (claim_id) REFERENCES claims (claim_id),
    INDEX idx_notifications_event_type (event_type),
    INDEX idx_notifications_sent_at (sent_at)
);

CREATE TABLE audit_events (
    audit_event_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    claim_id VARCHAR(32) NULL,
    event_type VARCHAR(40) NOT NULL,
    topic VARCHAR(80) NOT NULL,
    trace_id VARCHAR(64),
    payload_json JSON NOT NULL,
    recorded_at DATETIME NOT NULL,
    CONSTRAINT fk_audit_events_claim
        FOREIGN KEY (claim_id) REFERENCES claims (claim_id),
    INDEX idx_audit_events_claim (claim_id),
    INDEX idx_audit_events_event_type (event_type),
    INDEX idx_audit_events_recorded_at (recorded_at)
);
