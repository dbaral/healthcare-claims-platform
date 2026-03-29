USE healthcare_learning;

-- 1. Start with the main workflow view.
SELECT
    claim_id,
    patient_name,
    provider_name,
    total_claim_amount,
    validation_status,
    eligibility_status,
    fraud_status,
    adjudication_decision,
    payment_status,
    lifecycle_stage
FROM claim_lifecycle_summary
ORDER BY submitted_at DESC;

-- 2. Find claims that failed validation and show the exact errors.
SELECT
    c.claim_id,
    c.member_id,
    c.date_of_service,
    v.status AS validation_status,
    e.error_message
FROM claims c
JOIN claim_validations v
    ON v.claim_id = c.claim_id
LEFT JOIN claim_validation_errors e
    ON e.validation_id = v.validation_id
WHERE v.status = 'FAILED';

-- 3. Show every claim with patient, plan, provider, and facility details.
SELECT
    c.claim_id,
    CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
    m.member_id,
    pl.plan_name,
    pr.provider_name,
    f.facility_name,
    c.claim_type,
    c.total_claim_amount
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
ORDER BY c.claim_id;

-- 4. Which claims never reached payment, and why not?
SELECT
    claim_id,
    validation_status,
    eligibility_status,
    fraud_status,
    adjudication_decision,
    lifecycle_stage
FROM claim_lifecycle_summary
WHERE payment_status IS NULL
ORDER BY claim_id;

-- 5. Aggregate payment totals by provider.
SELECT
    provider_name,
    submitted_claims,
    total_billed_amount,
    total_payable_amount,
    total_paid_amount
FROM provider_payment_summary
ORDER BY total_paid_amount DESC;

-- 6. Find members who submitted more than one claim.
SELECT
    m.member_id,
    CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
    COUNT(*) AS claim_count,
    ROUND(SUM(c.total_claim_amount), 2) AS total_billed
FROM members m
JOIN patients p
    ON p.patient_id = m.patient_id
JOIN claims c
    ON c.member_id = m.member_id
GROUP BY
    m.member_id,
    patient_name
HAVING COUNT(*) > 1
ORDER BY claim_count DESC, total_billed DESC;

-- 7. Rank providers by total paid amount with a window function.
SELECT
    provider_name,
    total_paid_amount,
    DENSE_RANK() OVER (ORDER BY total_paid_amount DESC) AS payment_rank
FROM provider_payment_summary
ORDER BY payment_rank, provider_name;

-- 8. Compare billed, patient responsibility, and payable amounts by claim.
SELECT
    c.claim_id,
    c.total_claim_amount,
    c.patient_responsibility,
    a.payable_amount,
    ROUND(c.total_claim_amount - COALESCE(a.payable_amount, 0), 2) AS non_payable_amount
FROM claims c
LEFT JOIN adjudications a
    ON a.claim_id = c.claim_id
ORDER BY c.total_claim_amount DESC;

-- 9. Explore audit JSON for denied or exceptional outcomes.
SELECT
    claim_id,
    event_type,
    JSON_EXTRACT(payload_json, '$.adjudicationDecision') AS adjudication_decision,
    JSON_EXTRACT(payload_json, '$.ruleTriggered') AS rule_triggered,
    JSON_EXTRACT(payload_json, '$.errors') AS validation_errors,
    recorded_at
FROM audit_events
WHERE event_type IN ('claim-validation-failed', 'claim-fraud-flagged', 'claim-adjudicated')
ORDER BY recorded_at DESC;

-- 10. Monthly workflow snapshot.
SELECT
    DATE_FORMAT(submitted_at, '%Y-%m') AS claim_month,
    COUNT(*) AS submitted_claims,
    ROUND(SUM(total_claim_amount), 2) AS total_billed,
    SUM(CASE WHEN lifecycle_stage = 'PAID' THEN 1 ELSE 0 END) AS paid_claims,
    SUM(CASE WHEN lifecycle_stage = 'FRAUD_REVIEW' THEN 1 ELSE 0 END) AS fraud_review_claims,
    SUM(CASE WHEN lifecycle_stage = 'VALIDATION_FAILED' THEN 1 ELSE 0 END) AS validation_failed_claims
FROM claim_lifecycle_summary
GROUP BY DATE_FORMAT(submitted_at, '%Y-%m')
ORDER BY claim_month;
