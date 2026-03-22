# Claim Validation Service

This service consumes `claim-submitted`, validates the claim payload, stores the validation result, and publishes either `claim-validated` or `claim-validation-failed`.

## Responsibilities

- Consume `claim-submitted` from Kafka.
- Validate member ID, diagnosis codes, procedure codes, and date of service.
- Persist the validation result in PostgreSQL.
- Publish success or failure events for downstream services.

## Run locally

1. Start shared infrastructure from the repository root:
   `docker compose up -d zookeeper kafka claims-postgres claim-validation-postgres`
2. Open `services/claim-validation-service` in IntelliJ as a Maven project.
3. Run `ClaimValidationServiceApplication`.

## Query API

- `GET /api/v1/claim-validations`
- `GET /api/v1/claim-validations/{claimId}`

Swagger UI:

- `http://localhost:8092/swagger-ui.html`

## What to compare with claim-intake-service

- `ClaimSubmittedListener` is the consumer-side equivalent of the intake REST controller.
- `ClaimValidationProcessor` is the use-case class, like `ClaimApplicationService`.
- `KafkaClaimValidationEventPublisher` is the producer that emits downstream events.
