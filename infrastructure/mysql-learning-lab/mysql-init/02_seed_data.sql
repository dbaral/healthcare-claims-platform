USE healthcare_learning;

INSERT INTO plans (
    plan_id,
    plan_name,
    plan_type,
    payer_name,
    active,
    deductible_amount,
    out_of_pocket_max
) VALUES
    ('PLN-1001', 'Silver HMO', 'HMO', 'Apex Health', 1, 1500.00, 6500.00),
    ('PLN-1002', 'Gold PPO', 'PPO', 'Apex Health', 1, 750.00, 4500.00),
    ('PLN-2001', 'Family Choice EPO', 'EPO', 'Northwind Care', 1, 1200.00, 5500.00),
    ('PLN-3001', 'Senior Advantage', 'MEDICARE_ADVANTAGE', 'WellSpring Medicare', 1, 300.00, 3200.00);

INSERT INTO patients (
    patient_id,
    mrn,
    first_name,
    last_name,
    date_of_birth,
    sex,
    phone,
    email,
    city,
    state_code,
    created_at
) VALUES
    (1, 'MRN-100001', 'Maya', 'Patel', '1982-04-12', 'F', '617-555-0101', 'maya.patel@example.com', 'Boston', 'MA', '2025-01-03 09:00:00'),
    (2, 'MRN-100002', 'Jordan', 'Lee', '1975-09-03', 'M', '312-555-0102', 'jordan.lee@example.com', 'Chicago', 'IL', '2024-09-12 10:15:00'),
    (3, 'MRN-100003', 'Elena', 'Gomez', '1990-01-22', 'F', '602-555-0103', 'elena.gomez@example.com', 'Phoenix', 'AZ', '2024-01-08 11:20:00'),
    (4, 'MRN-100004', 'Marcus', 'Chen', '1968-11-15', 'M', '206-555-0104', 'marcus.chen@example.com', 'Seattle', 'WA', '2024-11-05 08:30:00'),
    (5, 'MRN-100005', 'Sofia', 'Rivera', '2018-06-20', 'F', '512-555-0105', 'sofia.rivera@example.com', 'Austin', 'TX', '2025-06-12 12:00:00'),
    (6, 'MRN-100006', 'Noah', 'Campbell', '1988-02-08', 'M', '303-555-0106', 'noah.campbell@example.com', 'Denver', 'CO', '2025-03-15 09:45:00'),
    (7, 'MRN-100007', 'Priya', 'Nair', '1994-12-02', 'F', '408-555-0107', 'priya.nair@example.com', 'San Jose', 'CA', '2024-05-18 13:00:00'),
    (8, 'MRN-100008', 'Daniel', 'Brooks', '1959-07-19', 'M', '813-555-0108', 'daniel.brooks@example.com', 'Tampa', 'FL', '2023-01-10 14:25:00'),
    (9, 'MRN-100009', 'Olivia', 'Turner', '2001-05-24', 'F', '404-555-0109', 'olivia.turner@example.com', 'Atlanta', 'GA', '2025-08-02 16:10:00'),
    (10, 'MRN-100010', 'Isaac', 'Flores', '1972-10-30', 'M', '505-555-0110', 'isaac.flores@example.com', 'Albuquerque', 'NM', '2024-02-14 11:35:00'),
    (11, 'MRN-100011', 'Chloe', 'Bennett', '2012-03-11', 'F', '503-555-0111', 'chloe.bennett@example.com', 'Portland', 'OR', '2025-01-12 09:05:00'),
    (12, 'MRN-100012', 'Henry', 'Watson', '1985-08-28', 'M', '704-555-0112', 'henry.watson@example.com', 'Charlotte', 'NC', '2024-07-03 15:40:00');

INSERT INTO members (
    member_id,
    patient_id,
    plan_id,
    relationship_to_subscriber,
    eligibility_status,
    coverage_start_date,
    coverage_end_date
) VALUES
    ('MBR-1001', 1, 'PLN-1002', 'SELF', 'ACTIVE', '2025-01-01', NULL),
    ('MBR-1002', 2, 'PLN-1001', 'SELF', 'ACTIVE', '2024-09-01', NULL),
    ('MBR-INACTIVE-3003', 3, 'PLN-2001', 'SELF', 'INACTIVE', '2024-01-01', '2026-02-28'),
    ('MBR-1004', 4, 'PLN-3001', 'SELF', 'ACTIVE', '2024-11-01', NULL),
    ('MBR-1005', 5, 'PLN-2001', 'CHILD', 'ACTIVE', '2025-06-01', NULL),
    ('MBR-1006', 6, 'PLN-1001', 'SELF', 'ACTIVE', '2025-03-01', NULL),
    ('MBR-1007', 7, 'PLN-1002', 'SELF', 'ACTIVE', '2024-05-15', NULL),
    ('MBR-1008', 8, 'PLN-3001', 'SELF', 'ACTIVE', '2023-01-01', NULL),
    ('MBR-1009', 9, 'PLN-1001', 'SELF', 'ACTIVE', '2025-08-01', NULL),
    ('MBR-1010', 10, 'PLN-1002', 'SELF', 'ACTIVE', '2024-02-01', NULL),
    ('MBR-1011', 11, 'PLN-2001', 'CHILD', 'ACTIVE', '2025-01-10', NULL),
    ('MBR-1012', 12, 'PLN-1001', 'SELF', 'ACTIVE', '2024-07-01', NULL);

INSERT INTO providers (
    provider_id,
    provider_name,
    specialty,
    npi,
    network_status,
    city,
    state_code
) VALUES
    ('PRV-7001', 'Lakeside Family Medicine', 'Family Medicine', '1144223344', 'IN_NETWORK', 'Boston', 'MA'),
    ('PRV-7002', 'Metro Cardiology Associates', 'Cardiology', '1144223345', 'IN_NETWORK', 'Chicago', 'IL'),
    ('PRV-7003', 'Sunrise Orthopedics', 'Orthopedics', '1144223346', 'IN_NETWORK', 'Seattle', 'WA'),
    ('PRV-7004', 'Green Valley Pediatrics', 'Pediatrics', '1144223347', 'IN_NETWORK', 'Austin', 'TX'),
    ('PRV-7005', 'Harbor Physical Therapy', 'Physical Therapy', '1144223348', 'IN_NETWORK', 'San Jose', 'CA'),
    ('PRV-7006', 'Downtown Imaging Center', 'Diagnostic Imaging', '1144223349', 'IN_NETWORK', 'Atlanta', 'GA'),
    ('PRV-RISK-9001', 'Risk Review Surgical Center', 'Ambulatory Surgery', '1144223350', 'OUT_OF_NETWORK', 'Phoenix', 'AZ');

INSERT INTO facilities (
    facility_id,
    facility_name,
    facility_type,
    city,
    state_code
) VALUES
    ('FAC-100', 'Lakeside Outpatient Clinic', 'OUTPATIENT_CENTER', 'Boston', 'MA'),
    ('FAC-101', 'Metro Heart Institute', 'SPECIALTY_CENTER', 'Chicago', 'IL'),
    ('FAC-102', 'Sunrise Medical Center', 'HOSPITAL', 'Seattle', 'WA'),
    ('FAC-103', 'Downtown Imaging Pavilion', 'IMAGING_CENTER', 'Atlanta', 'GA'),
    ('FAC-104', 'Harbor Rehab Campus', 'REHABILITATION_CENTER', 'San Jose', 'CA');

INSERT INTO claims (
    claim_id,
    member_id,
    provider_id,
    facility_id,
    claim_type,
    date_of_service,
    submitted_at,
    total_claim_amount,
    currency,
    patient_responsibility,
    notes,
    status
) VALUES
    ('CLM-10001', 'MBR-1001', 'PRV-7001', 'FAC-100', 'OUTPATIENT', '2026-03-20', '2026-03-21 09:12:00', 1250.75, 'USD', 100.00, 'Routine diabetes follow-up visit', 'SUBMITTED'),
    ('CLM-10002', 'MBR-1002', 'PRV-7002', 'FAC-101', 'OUTPATIENT', '2026-03-18', '2026-03-18 14:30:00', 320.00, 'USD', 0.00, 'Chest pain evaluation', 'SUBMITTED'),
    ('CLM-10003', 'MBR-INACTIVE-3003', 'PRV-7003', 'FAC-102', 'INPATIENT', '2026-03-12', '2026-03-12 11:05:00', 890.00, 'USD', 50.00, 'Observation stay after ankle injury', 'SUBMITTED'),
    ('CLM-10004', 'MBR-1004', 'PRV-RISK-9001', 'FAC-102', 'SURGERY', '2026-03-10', '2026-03-10 16:45:00', 15000.00, 'USD', 500.00, 'Surgical claim routed for review', 'SUBMITTED'),
    ('CLM-10005', 'MBR-1005', 'PRV-7004', 'FAC-100', 'OUTPATIENT', '2026-03-07', '2026-03-07 08:15:00', 600.00, 'USD', 600.00, 'Preventive pediatric visit with vaccine counseling', 'SUBMITTED'),
    ('CLM-10006', 'MBR-1006', 'PRV-7001', 'FAC-100', 'OUTPATIENT', '2026-04-02', '2026-03-28 09:40:00', 210.00, 'USD', 30.00, 'Claim submitted before date of service occurred', 'SUBMITTED'),
    ('CLM-10007', 'MBR-1007', 'PRV-7005', 'FAC-104', 'THERAPY', '2026-02-27', '2026-02-28 10:25:00', 2200.00, 'USD', 150.00, 'Post-operative physical therapy block', 'SUBMITTED'),
    ('CLM-10008', 'MBR-1008', 'PRV-7002', 'FAC-101', 'OUTPATIENT', '2026-02-21', '2026-02-21 13:55:00', 9800.00, 'USD', 0.00, 'Cardiology diagnostic workup', 'SUBMITTED'),
    ('CLM-10009', 'MBR-1009', 'PRV-7006', 'FAC-103', 'DIAGNOSTIC', '2026-02-14', '2026-02-14 07:50:00', 75.00, 'USD', 20.00, 'Follow-up chest x-ray', 'SUBMITTED'),
    ('CLM-10010', 'MBR-1010', 'PRV-7003', 'FAC-102', 'INPATIENT', '2026-02-05', '2026-02-05 18:20:00', 12800.00, 'USD', 0.00, 'High-dollar orthopedic admission', 'SUBMITTED'),
    ('CLM-10011', 'MBR-1011', 'PRV-7004', 'FAC-100', 'OUTPATIENT', '2026-01-29', '2026-01-29 15:10:00', 470.00, 'USD', 0.00, 'Pediatric sick visit', 'SUBMITTED'),
    ('CLM-10012', 'MBR-1012', 'PRV-7001', 'FAC-100', 'OUTPATIENT', '2026-01-15', '2026-01-15 09:05:00', 1420.00, 'USD', 200.00, 'Hypertension management follow-up', 'SUBMITTED'),
    ('CLM-10013', 'MBR-1001', 'PRV-7006', 'FAC-103', 'DIAGNOSTIC', '2026-02-11', '2026-02-11 12:18:00', 680.00, 'USD', 50.00, 'MRI imaging claim', 'SUBMITTED'),
    ('CLM-10014', 'MBR-1002', 'PRV-7002', 'FAC-101', 'OUTPATIENT', '2026-01-08', '2026-01-08 10:40:00', 1500.00, 'USD', 0.00, 'Cardiology follow-up with stress testing', 'SUBMITTED');

INSERT INTO claim_diagnoses (
    claim_id,
    diagnosis_code,
    diagnosis_type,
    description
) VALUES
    ('CLM-10001', 'E11.9', 'PRIMARY', 'Type 2 diabetes mellitus without complications'),
    ('CLM-10001', 'Z79.84', 'SECONDARY', 'Long term use of oral hypoglycemic drugs'),
    ('CLM-10002', 'R07.9', 'PRIMARY', 'Chest pain, unspecified'),
    ('CLM-10003', 'S93.401A', 'PRIMARY', 'Sprain of unspecified ligament of right ankle'),
    ('CLM-10004', 'M17.11', 'PRIMARY', 'Unilateral primary osteoarthritis, right knee'),
    ('CLM-10005', 'Z00.129', 'PRIMARY', 'Routine child health exam without abnormal findings'),
    ('CLM-10006', 'J06.9', 'PRIMARY', 'Acute upper respiratory infection, unspecified'),
    ('CLM-10007', 'Z47.89', 'PRIMARY', 'Encounter for orthopedic aftercare'),
    ('CLM-10008', 'I20.9', 'PRIMARY', 'Angina pectoris, unspecified'),
    ('CLM-10008', 'E78.5', 'SECONDARY', 'Hyperlipidemia, unspecified'),
    ('CLM-10009', 'R91.8', 'PRIMARY', 'Other nonspecific abnormal finding of lung field'),
    ('CLM-10010', 'M16.11', 'PRIMARY', 'Unilateral primary osteoarthritis, right hip'),
    ('CLM-10011', 'J02.9', 'PRIMARY', 'Acute pharyngitis, unspecified'),
    ('CLM-10012', 'I10', 'PRIMARY', 'Essential hypertension'),
    ('CLM-10012', 'E78.2', 'SECONDARY', 'Mixed hyperlipidemia'),
    ('CLM-10013', 'M54.50', 'PRIMARY', 'Low back pain, unspecified'),
    ('CLM-10014', 'I25.10', 'PRIMARY', 'Atherosclerotic heart disease of native coronary artery without angina pectoris');

INSERT INTO claim_procedures (
    claim_id,
    procedure_code,
    procedure_description,
    units,
    amount
) VALUES
    ('CLM-10001', '99214', 'Established patient office visit', 1, 250.75),
    ('CLM-10001', '83036', 'Hemoglobin A1c test', 1, 1000.00),
    ('CLM-10002', '93000', 'Electrocardiogram', 1, 120.00),
    ('CLM-10002', '99213', 'Office visit', 1, 200.00),
    ('CLM-10003', '29515', 'Short leg splint application', 1, 890.00),
    ('CLM-10004', '27447', 'Knee arthroplasty', 1, 15000.00),
    ('CLM-10005', '99393', 'Preventive visit age 5 to 11', 1, 400.00),
    ('CLM-10005', '90460', 'Vaccine administration', 4, 200.00),
    ('CLM-10006', '99213', 'Office visit', 1, 210.00),
    ('CLM-10007', '97110', 'Therapeutic exercises', 8, 1200.00),
    ('CLM-10007', '97530', 'Therapeutic activities', 4, 1000.00),
    ('CLM-10008', '93458', 'Cardiac catheterization', 1, 8000.00),
    ('CLM-10008', '93306', 'Echocardiogram', 1, 1800.00),
    ('CLM-10009', '71046', 'Chest x-ray', 1, 75.00),
    ('CLM-10010', '27130', 'Total hip arthroplasty', 1, 12800.00),
    ('CLM-10011', '99213', 'Office visit', 1, 220.00),
    ('CLM-10011', '87880', 'Rapid strep test', 1, 250.00),
    ('CLM-10012', '99214', 'Established patient office visit', 1, 320.00),
    ('CLM-10012', '80053', 'Comprehensive metabolic panel', 1, 1100.00),
    ('CLM-10013', '72148', 'MRI lumbar spine without contrast', 1, 680.00),
    ('CLM-10014', '93015', 'Cardiovascular stress test', 1, 1500.00);

INSERT INTO claim_validations (
    validation_id,
    claim_id,
    member_id,
    provider_id,
    facility_id,
    claim_type,
    date_of_service,
    total_claim_amount,
    currency,
    patient_responsibility,
    status,
    processed_at
) VALUES
    (1, 'CLM-10001', 'MBR-1001', 'PRV-7001', 'FAC-100', 'OUTPATIENT', '2026-03-20', 1250.75, 'USD', 100.00, 'VALIDATED', '2026-03-21 09:20:00'),
    (2, 'CLM-10002', 'MBR-1002', 'PRV-7002', 'FAC-101', 'OUTPATIENT', '2026-03-18', 320.00, 'USD', 0.00, 'VALIDATED', '2026-03-18 14:36:00'),
    (3, 'CLM-10003', 'MBR-INACTIVE-3003', 'PRV-7003', 'FAC-102', 'INPATIENT', '2026-03-12', 890.00, 'USD', 50.00, 'VALIDATED', '2026-03-12 11:12:00'),
    (4, 'CLM-10004', 'MBR-1004', 'PRV-RISK-9001', 'FAC-102', 'SURGERY', '2026-03-10', 15000.00, 'USD', 500.00, 'VALIDATED', '2026-03-10 16:53:00'),
    (5, 'CLM-10005', 'MBR-1005', 'PRV-7004', 'FAC-100', 'OUTPATIENT', '2026-03-07', 600.00, 'USD', 600.00, 'VALIDATED', '2026-03-07 08:22:00'),
    (6, 'CLM-10006', 'MBR-1006', 'PRV-7001', 'FAC-100', 'OUTPATIENT', '2026-04-02', 210.00, 'USD', 30.00, 'FAILED', '2026-03-28 09:45:00'),
    (7, 'CLM-10007', 'MBR-1007', 'PRV-7005', 'FAC-104', 'THERAPY', '2026-02-27', 2200.00, 'USD', 150.00, 'VALIDATED', '2026-02-28 10:31:00'),
    (8, 'CLM-10008', 'MBR-1008', 'PRV-7002', 'FAC-101', 'OUTPATIENT', '2026-02-21', 9800.00, 'USD', 0.00, 'VALIDATED', '2026-02-21 14:02:00'),
    (9, 'CLM-10009', 'MBR-1009', 'PRV-7006', 'FAC-103', 'DIAGNOSTIC', '2026-02-14', 75.00, 'USD', 20.00, 'VALIDATED', '2026-02-14 07:56:00'),
    (10, 'CLM-10010', 'MBR-1010', 'PRV-7003', 'FAC-102', 'INPATIENT', '2026-02-05', 12800.00, 'USD', 0.00, 'VALIDATED', '2026-02-05 18:27:00'),
    (11, 'CLM-10011', 'MBR-1011', 'PRV-7004', 'FAC-100', 'OUTPATIENT', '2026-01-29', 470.00, 'USD', 0.00, 'VALIDATED', '2026-01-29 15:16:00'),
    (12, 'CLM-10012', 'MBR-1012', 'PRV-7001', 'FAC-100', 'OUTPATIENT', '2026-01-15', 1420.00, 'USD', 200.00, 'VALIDATED', '2026-01-15 09:11:00'),
    (13, 'CLM-10013', 'MBR-1001', 'PRV-7006', 'FAC-103', 'DIAGNOSTIC', '2026-02-11', 680.00, 'USD', 50.00, 'VALIDATED', '2026-02-11 12:25:00'),
    (14, 'CLM-10014', 'MBR-1002', 'PRV-7002', 'FAC-101', 'OUTPATIENT', '2026-01-08', 1500.00, 'USD', 0.00, 'VALIDATED', '2026-01-08 10:46:00');

INSERT INTO claim_validation_errors (
    validation_id,
    error_message
) VALUES
    (6, 'Date of service cannot be in the future');

INSERT INTO eligibility_checks (
    eligibility_check_id,
    claim_id,
    member_id,
    plan_id,
    status,
    reason,
    checked_at
) VALUES
    (1, 'CLM-10001', 'MBR-1001', 'PLN-1002', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-03-21 09:24:00'),
    (2, 'CLM-10002', 'MBR-1002', 'PLN-1001', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-03-18 14:39:00'),
    (3, 'CLM-10003', 'MBR-INACTIVE-3003', 'PLN-2001', 'INELIGIBLE', 'Member plan is inactive or member identifier is not eligible', '2026-03-12 11:15:00'),
    (4, 'CLM-10004', 'MBR-1004', 'PLN-3001', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-03-10 16:58:00'),
    (5, 'CLM-10005', 'MBR-1005', 'PLN-2001', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-03-07 08:25:00'),
    (6, 'CLM-10007', 'MBR-1007', 'PLN-1002', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-02-28 10:34:00'),
    (7, 'CLM-10008', 'MBR-1008', 'PLN-3001', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-02-21 14:06:00'),
    (8, 'CLM-10009', 'MBR-1009', 'PLN-1001', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-02-14 07:58:00'),
    (9, 'CLM-10010', 'MBR-1010', 'PLN-1002', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-02-05 18:30:00'),
    (10, 'CLM-10011', 'MBR-1011', 'PLN-2001', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-01-29 15:18:00'),
    (11, 'CLM-10012', 'MBR-1012', 'PLN-1001', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-01-15 09:13:00'),
    (12, 'CLM-10013', 'MBR-1001', 'PLN-1002', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-02-11 12:27:00'),
    (13, 'CLM-10014', 'MBR-1002', 'PLN-1001', 'ELIGIBLE', 'Member eligible for adjudication workflow', '2026-01-08 10:49:00');

INSERT INTO fraud_checks (
    fraud_check_id,
    claim_id,
    provider_id,
    status,
    rule_triggered,
    reviewed_by,
    reviewed_at,
    checked_at
) VALUES
    (1, 'CLM-10001', 'PRV-7001', 'CLEARED', NULL, NULL, NULL, '2026-03-21 09:28:00'),
    (2, 'CLM-10002', 'PRV-7002', 'CLEARED', NULL, NULL, NULL, '2026-03-18 14:42:00'),
    (3, 'CLM-10004', 'PRV-RISK-9001', 'FLAGGED', 'Claim matched fraud threshold or suspicious provider pattern', 'S. Patel', '2026-03-10 17:10:00', '2026-03-10 17:02:00'),
    (4, 'CLM-10005', 'PRV-7004', 'CLEARED', NULL, NULL, NULL, '2026-03-07 08:28:00'),
    (5, 'CLM-10007', 'PRV-7005', 'CLEARED', NULL, NULL, NULL, '2026-02-28 10:38:00'),
    (6, 'CLM-10008', 'PRV-7002', 'CLEARED', NULL, NULL, NULL, '2026-02-21 14:10:00'),
    (7, 'CLM-10009', 'PRV-7006', 'CLEARED', NULL, NULL, NULL, '2026-02-14 08:00:00'),
    (8, 'CLM-10010', 'PRV-7003', 'FLAGGED', 'Claim matched fraud threshold or suspicious provider pattern', 'J. Morales', '2026-02-05 18:45:00', '2026-02-05 18:35:00'),
    (9, 'CLM-10011', 'PRV-7004', 'CLEARED', NULL, NULL, NULL, '2026-01-29 15:20:00'),
    (10, 'CLM-10012', 'PRV-7001', 'CLEARED', NULL, NULL, NULL, '2026-01-15 09:15:00'),
    (11, 'CLM-10013', 'PRV-7006', 'CLEARED', NULL, NULL, NULL, '2026-02-11 12:29:00'),
    (12, 'CLM-10014', 'PRV-7002', 'CLEARED', NULL, NULL, NULL, '2026-01-08 10:52:00');

INSERT INTO adjudications (
    adjudication_id,
    claim_id,
    decision,
    total_claim_amount,
    patient_responsibility,
    payable_amount,
    denial_reason,
    processed_at
) VALUES
    (1, 'CLM-10001', 'PARTIAL', 1250.75, 100.00, 1150.75, NULL, '2026-03-21 09:35:00'),
    (2, 'CLM-10002', 'APPROVED', 320.00, 0.00, 320.00, NULL, '2026-03-18 14:48:00'),
    (3, 'CLM-10005', 'DENIED', 600.00, 600.00, 0.00, 'Patient responsibility equals allowed amount', '2026-03-07 08:35:00'),
    (4, 'CLM-10007', 'PARTIAL', 2200.00, 150.00, 2050.00, NULL, '2026-02-28 10:44:00'),
    (5, 'CLM-10008', 'APPROVED', 9800.00, 0.00, 9800.00, NULL, '2026-02-21 14:15:00'),
    (6, 'CLM-10009', 'PARTIAL', 75.00, 20.00, 55.00, NULL, '2026-02-14 08:06:00'),
    (7, 'CLM-10011', 'APPROVED', 470.00, 0.00, 470.00, NULL, '2026-01-29 15:24:00'),
    (8, 'CLM-10012', 'PARTIAL', 1420.00, 200.00, 1220.00, NULL, '2026-01-15 09:20:00'),
    (9, 'CLM-10013', 'PARTIAL', 680.00, 50.00, 630.00, NULL, '2026-02-11 12:32:00'),
    (10, 'CLM-10014', 'APPROVED', 1500.00, 0.00, 1500.00, NULL, '2026-01-08 10:56:00');

INSERT INTO payments (
    payment_id,
    claim_id,
    member_id,
    provider_id,
    amount,
    currency,
    status,
    payment_method,
    reference_number,
    processed_at
) VALUES
    ('PAY-90001', 'CLM-10001', 'MBR-1001', 'PRV-7001', 1150.75, 'USD', 'PROCESSED', 'EFT', 'PMT-REF-90001', '2026-03-21 09:42:00'),
    ('PAY-90002', 'CLM-10002', 'MBR-1002', 'PRV-7002', 320.00, 'USD', 'PROCESSED', 'EFT', 'PMT-REF-90002', '2026-03-18 15:00:00'),
    ('PAY-90003', 'CLM-10007', 'MBR-1007', 'PRV-7005', 2050.00, 'USD', 'PROCESSED', 'EFT', 'PMT-REF-90003', '2026-02-28 10:52:00'),
    ('PAY-90004', 'CLM-10008', 'MBR-1008', 'PRV-7002', 9800.00, 'USD', 'PROCESSED', 'EFT', 'PMT-REF-90004', '2026-02-21 14:22:00'),
    ('PAY-90005', 'CLM-10009', 'MBR-1009', 'PRV-7006', 55.00, 'USD', 'PROCESSED', 'EFT', 'PMT-REF-90005', '2026-02-14 08:15:00'),
    ('PAY-90006', 'CLM-10011', 'MBR-1011', 'PRV-7004', 470.00, 'USD', 'PROCESSED', 'EFT', 'PMT-REF-90006', '2026-01-29 15:30:00'),
    ('PAY-90007', 'CLM-10012', 'MBR-1012', 'PRV-7001', 1220.00, 'USD', 'PROCESSED', 'EFT', 'PMT-REF-90007', '2026-01-15 09:28:00'),
    ('PAY-90008', 'CLM-10013', 'MBR-1001', 'PRV-7006', 630.00, 'USD', 'PROCESSED', 'EFT', 'PMT-REF-90008', '2026-02-11 12:40:00'),
    ('PAY-90009', 'CLM-10014', 'MBR-1002', 'PRV-7002', 1500.00, 'USD', 'PROCESSED', 'EFT', 'PMT-REF-90009', '2026-01-08 11:05:00');

INSERT INTO notifications (
    notification_id,
    claim_id,
    event_type,
    channel,
    recipient,
    message,
    status,
    sent_at
) VALUES
    ('NTF-10001', 'CLM-10001', 'payment-processed', 'INTERNAL', 'billing@lakeside.example', 'Payment processed for claim CLM-10001', 'SENT', '2026-03-21 09:43:00'),
    ('NTF-10002', 'CLM-10002', 'payment-processed', 'INTERNAL', 'billing@metrocardiology.example', 'Payment processed for claim CLM-10002', 'SENT', '2026-03-18 15:02:00'),
    ('NTF-10003', 'CLM-10003', 'member-ineligible', 'INTERNAL', 'member-services@northwindcare.example', 'Member ineligible for claim CLM-10003', 'SENT', '2026-03-12 11:18:00'),
    ('NTF-10004', 'CLM-10004', 'claim-fraud-flagged', 'INTERNAL', 'fraud@apexhealth.example', 'Claim flagged for fraud review CLM-10004', 'SENT', '2026-03-10 17:05:00'),
    ('NTF-10005', 'CLM-10005', 'claim-adjudicated', 'INTERNAL', 'appeals@northwindcare.example', 'Claim denied during adjudication CLM-10005', 'SENT', '2026-03-07 08:37:00'),
    ('NTF-10006', 'CLM-10006', 'claim-validation-failed', 'INTERNAL', 'claim-intake@healthcare.local', 'Claim validation failed for claim CLM-10006', 'SENT', '2026-03-28 09:47:00'),
    ('NTF-10007', 'CLM-10007', 'payment-processed', 'INTERNAL', 'billing@harborpt.example', 'Payment processed for claim CLM-10007', 'SENT', '2026-02-28 10:54:00'),
    ('NTF-10008', 'CLM-10008', 'payment-processed', 'INTERNAL', 'billing@metrocardiology.example', 'Payment processed for claim CLM-10008', 'SENT', '2026-02-21 14:24:00'),
    ('NTF-10009', 'CLM-10009', 'payment-processed', 'INTERNAL', 'billing@downtownimaging.example', 'Payment processed for claim CLM-10009', 'SENT', '2026-02-14 08:17:00'),
    ('NTF-10010', 'CLM-10010', 'claim-fraud-flagged', 'INTERNAL', 'fraud@apexhealth.example', 'Claim flagged for fraud review CLM-10010', 'SENT', '2026-02-05 18:38:00'),
    ('NTF-10011', 'CLM-10011', 'payment-processed', 'INTERNAL', 'billing@greenvalleypeds.example', 'Payment processed for claim CLM-10011', 'SENT', '2026-01-29 15:31:00'),
    ('NTF-10012', 'CLM-10012', 'payment-processed', 'INTERNAL', 'billing@lakeside.example', 'Payment processed for claim CLM-10012', 'SENT', '2026-01-15 09:30:00'),
    ('NTF-10013', 'CLM-10013', 'payment-processed', 'INTERNAL', 'billing@downtownimaging.example', 'Payment processed for claim CLM-10013', 'SENT', '2026-02-11 12:42:00'),
    ('NTF-10014', 'CLM-10014', 'payment-processed', 'INTERNAL', 'billing@metrocardiology.example', 'Payment processed for claim CLM-10014', 'SENT', '2026-01-08 11:07:00');

INSERT INTO audit_events (
    claim_id,
    event_type,
    topic,
    trace_id,
    payload_json,
    recorded_at
)
SELECT
    c.claim_id,
    'claim-submitted',
    'claim-submitted',
    CONCAT('TRACE-', c.claim_id),
    JSON_OBJECT(
        'claimId', c.claim_id,
        'memberId', c.member_id,
        'providerId', c.provider_id,
        'facilityId', c.facility_id,
        'claimType', c.claim_type,
        'totalClaimAmount', c.total_claim_amount,
        'patientResponsibility', c.patient_responsibility
    ),
    c.submitted_at
FROM claims c;

INSERT INTO audit_events (
    claim_id,
    event_type,
    topic,
    trace_id,
    payload_json,
    recorded_at
)
SELECT
    v.claim_id,
    CASE
        WHEN v.status = 'VALIDATED' THEN 'claim-validated'
        ELSE 'claim-validation-failed'
    END,
    CASE
        WHEN v.status = 'VALIDATED' THEN 'claim-validated'
        ELSE 'claim-validation-failed'
    END,
    CONCAT('TRACE-', v.claim_id),
    CASE
        WHEN v.status = 'VALIDATED' THEN JSON_OBJECT(
            'claimId', v.claim_id,
            'status', v.status,
            'processedAt', DATE_FORMAT(v.processed_at, '%Y-%m-%d %H:%i:%s')
        )
        ELSE JSON_OBJECT(
            'claimId', v.claim_id,
            'status', v.status,
            'errors',
            COALESCE(
                (
                    SELECT JSON_ARRAYAGG(error_message)
                    FROM claim_validation_errors e
                    WHERE e.validation_id = v.validation_id
                ),
                JSON_ARRAY()
            )
        )
    END,
    v.processed_at
FROM claim_validations v;

INSERT INTO audit_events (
    claim_id,
    event_type,
    topic,
    trace_id,
    payload_json,
    recorded_at
)
SELECT
    e.claim_id,
    CASE
        WHEN e.status = 'ELIGIBLE' THEN 'member-eligible'
        ELSE 'member-ineligible'
    END,
    CASE
        WHEN e.status = 'ELIGIBLE' THEN 'member-eligible'
        ELSE 'member-ineligible'
    END,
    CONCAT('TRACE-', e.claim_id),
    JSON_OBJECT(
        'claimId', e.claim_id,
        'memberId', e.member_id,
        'planId', e.plan_id,
        'status', e.status,
        'reason', e.reason
    ),
    e.checked_at
FROM eligibility_checks e;

INSERT INTO audit_events (
    claim_id,
    event_type,
    topic,
    trace_id,
    payload_json,
    recorded_at
)
SELECT
    f.claim_id,
    CASE
        WHEN f.status = 'CLEARED' THEN 'claim-fraud-cleared'
        ELSE 'claim-fraud-flagged'
    END,
    CASE
        WHEN f.status = 'CLEARED' THEN 'claim-fraud-cleared'
        ELSE 'claim-fraud-flagged'
    END,
    CONCAT('TRACE-', f.claim_id),
    JSON_OBJECT(
        'claimId', f.claim_id,
        'providerId', f.provider_id,
        'status', f.status,
        'ruleTriggered', f.rule_triggered
    ),
    f.checked_at
FROM fraud_checks f;

INSERT INTO audit_events (
    claim_id,
    event_type,
    topic,
    trace_id,
    payload_json,
    recorded_at
)
SELECT
    a.claim_id,
    'claim-adjudicated',
    'claim-adjudicated',
    CONCAT('TRACE-', a.claim_id),
    JSON_OBJECT(
        'claimId', a.claim_id,
        'adjudicationDecision', a.decision,
        'payableAmount', a.payable_amount,
        'denialReason', a.denial_reason
    ),
    a.processed_at
FROM adjudications a;

INSERT INTO audit_events (
    claim_id,
    event_type,
    topic,
    trace_id,
    payload_json,
    recorded_at
)
SELECT
    p.claim_id,
    'payment-processed',
    'payment-processed',
    CONCAT('TRACE-', p.claim_id),
    JSON_OBJECT(
        'claimId', p.claim_id,
        'paymentId', p.payment_id,
        'amount', p.amount,
        'status', p.status
    ),
    p.processed_at
FROM payments p;
