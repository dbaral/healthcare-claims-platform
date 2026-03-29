USE healthcare_learning;

CREATE VIEW claim_lifecycle_summary AS
SELECT
    c.claim_id,
    CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
    m.member_id,
    pl.plan_name,
    pr.provider_name,
    f.facility_name,
    c.claim_type,
    c.date_of_service,
    c.submitted_at,
    c.total_claim_amount,
    c.patient_responsibility,
    v.status AS validation_status,
    COALESCE(ve.error_count, 0) AS validation_error_count,
    ec.status AS eligibility_status,
    fc.status AS fraud_status,
    a.decision AS adjudication_decision,
    a.payable_amount,
    pay.status AS payment_status,
    pay.processed_at AS payment_processed_at,
    COALESCE(n.notification_count, 0) AS notification_count,
    n.last_notification_at,
    CASE
        WHEN pay.payment_id IS NOT NULL THEN 'PAID'
        WHEN a.decision = 'DENIED' THEN 'DENIED'
        WHEN fc.status = 'FLAGGED' THEN 'FRAUD_REVIEW'
        WHEN ec.status = 'INELIGIBLE' THEN 'INELIGIBLE'
        WHEN v.status = 'FAILED' THEN 'VALIDATION_FAILED'
        WHEN a.adjudication_id IS NOT NULL THEN 'ADJUDICATED'
        ELSE 'IN_PROGRESS'
    END AS lifecycle_stage
FROM claims c
JOIN members m
    ON m.member_id = c.member_id
JOIN patients p
    ON p.patient_id = m.patient_id
JOIN plans pl
    ON pl.plan_id = m.plan_id
JOIN providers pr
    ON pr.provider_id = c.provider_id
LEFT JOIN facilities f
    ON f.facility_id = c.facility_id
LEFT JOIN claim_validations v
    ON v.claim_id = c.claim_id
LEFT JOIN (
    SELECT
        validation_id,
        COUNT(*) AS error_count
    FROM claim_validation_errors
    GROUP BY validation_id
) ve
    ON ve.validation_id = v.validation_id
LEFT JOIN eligibility_checks ec
    ON ec.claim_id = c.claim_id
LEFT JOIN fraud_checks fc
    ON fc.claim_id = c.claim_id
LEFT JOIN adjudications a
    ON a.claim_id = c.claim_id
LEFT JOIN payments pay
    ON pay.claim_id = c.claim_id
LEFT JOIN (
    SELECT
        claim_id,
        COUNT(*) AS notification_count,
        MAX(sent_at) AS last_notification_at
    FROM notifications
    GROUP BY claim_id
) n
    ON n.claim_id = c.claim_id;

CREATE VIEW provider_payment_summary AS
SELECT
    pr.provider_id,
    pr.provider_name,
    pr.specialty,
    COUNT(DISTINCT c.claim_id) AS submitted_claims,
    ROUND(COALESCE(SUM(c.total_claim_amount), 0), 2) AS total_billed_amount,
    ROUND(COALESCE(SUM(a.payable_amount), 0), 2) AS total_payable_amount,
    ROUND(COALESCE(SUM(pay.amount), 0), 2) AS total_paid_amount,
    ROUND(AVG(pay.amount), 2) AS avg_paid_claim_amount,
    SUM(CASE WHEN fc.status = 'FLAGGED' THEN 1 ELSE 0 END) AS fraud_flags
FROM providers pr
LEFT JOIN claims c
    ON c.provider_id = pr.provider_id
LEFT JOIN adjudications a
    ON a.claim_id = c.claim_id
LEFT JOIN payments pay
    ON pay.claim_id = c.claim_id
LEFT JOIN fraud_checks fc
    ON fc.claim_id = c.claim_id
GROUP BY
    pr.provider_id,
    pr.provider_name,
    pr.specialty;
