# MySQL Learning Lab

This folder gives you a clean MySQL dataset based on the healthcare claims workflow in this repository so you can practice SQL in DBeaver.

## What is included

- A dedicated MySQL 8.4 container on port `3310`
- A normalized learning schema for patients, members, plans, providers, facilities, claims, validations, eligibility checks, fraud checks, adjudications, payments, notifications, and audit events
- Seed data covering approved, partial, denied, fraud-flagged, ineligible, and validation-failed claims
- Two helper views: `claim_lifecycle_summary` and `provider_payment_summary`
- A ready-to-run practice file at `practice_queries.sql`

## Start the database

From the repository root:

```powershell
docker compose -f infrastructure/mysql-learning-lab/docker-compose.yml up -d
```

The first startup loads all SQL files from `mysql-init/`.

## DBeaver connection

Use these values in DBeaver:

- Host: `localhost`
- Port: `3310`
- Database: `healthcare_learning`
- Username: `learner`
- Password: `learnerpw`

If you prefer the root account:

- Username: `root`
- Password: `rootpw`

## Reset the data

MySQL only runs the init scripts when the data directory is empty. To rebuild the lab from scratch:

```powershell
docker compose -f infrastructure/mysql-learning-lab/docker-compose.yml down -v
docker compose -f infrastructure/mysql-learning-lab/docker-compose.yml up -d
```

## Good tables to query first

- `claims`
- `claim_diagnoses`
- `claim_procedures`
- `claim_validations`
- `eligibility_checks`
- `fraud_checks`
- `adjudications`
- `payments`
- `notifications`
- `audit_events`

## First queries to try

```sql
USE healthcare_learning;

SELECT * FROM claim_lifecycle_summary ORDER BY submitted_at DESC;

SELECT provider_name, total_paid_amount
FROM provider_payment_summary
ORDER BY total_paid_amount DESC;
```

If you want, the next step can be a guided list of beginner-to-advanced MySQL exercises using this exact dataset.
