# Claim Intake Service

This service is the entry point for the claim workflow.

## Responsibilities

- Accept a claim submission through REST.
- Persist the initial claim record in PostgreSQL.
- Publish a `claim-submitted` event to Kafka.

## Run locally

1. Start shared infrastructure from the repository root:
   `docker compose up -d kafka zookeeper claims-postgres`
2. Open `services/claim-intake-service` in IntelliJ as a Maven project.
3. Run `ClaimIntakeServiceApplication`.

## API

- `POST /api/v1/claims`
- `GET /api/v1/claims`
- `GET /api/v1/claims/{claimId}`

Swagger UI:

- `http://localhost:8091/swagger-ui.html`

## Sample request

```json
{
  "memberId": "MBR-9001",
  "providerId": "PRV-7001",
  "facilityId": "FAC-100",
  "claimType": "OUTPATIENT",
  "dateOfService": "2026-03-20",
  "totalClaimAmount": 1250.75,
  "currency": "USD",
  "patientResponsibility": 100.00,
  "notes": "Routine diabetes follow-up visit",
  "diagnoses": [
    {
      "code": "E11.9",
      "type": "PRIMARY"
    }
  ],
  "procedures": [
    {
      "code": "99213",
      "units": 1,
      "amount": 1250.75
    }
  ]
}
```
