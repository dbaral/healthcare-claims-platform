# Healthcare Claims Platform

Event-driven healthcare claims processing platform built with Java 17, Spring Boot, Kafka, PostgreSQL, Docker, and microservices architecture.

## Architecture

The claim workflow is split into independent services:

- `claim-intake-service`
- `claim-validation-service`
- `eligibility-service`
- `fraud-check-service`
- `adjudication-service`
- `payment-service`
- `notification-service`
- `audit-service`

## Core Tech Stack

- Java 17
- Spring Boot
- Spring Kafka
- Spring Data JPA
- PostgreSQL
- Docker Compose
- OpenAPI / Swagger
- Micrometer / Prometheus-ready Actuator endpoints

## Event Flow

`claim-submitted` -> `claim-validated` -> `member-eligible` -> `claim-fraud-cleared` -> `claim-adjudicated` -> `payment-processed` -> `claim-notification-sent`

Failure topics include:

- `claim-validation-failed`
- `member-ineligible`
- `claim-fraud-flagged`

## Local Run

Start infrastructure:

```powershell
docker compose up -d zookeeper kafka kafka-ui claims-postgres claim-validation-postgres payment-postgres notification-postgres audit-postgres
```

## Infrastructure

Deployment-oriented assets live under [infrastructure/README.md](C:/dev/healthcare-platform/infrastructure/README.md), including Docker build docs and starter Kubernetes manifests for the core claims workflow.
Run services from IntelliJ in workflow order:

1. `audit-service`
2. `notification-service`
3. `payment-service`
4. `adjudication-service`
5. `fraud-check-service`
6. `eligibility-service`
7. `claim-validation-service`
8. `claim-intake-service`

## Project Goals

- Demonstrate event-driven microservices design
- Model healthcare claim lifecycle processing
- Show producer/consumer Kafka patterns
- Persist service-owned state in PostgreSQL
- Provide a practical portfolio project for interviews

## CI

This repository includes a GitHub Actions workflow at [.github/workflows/ci.yml](C:/dev/healthcare-platform/.github/workflows/ci.yml) that runs Maven tests for each service on pushes and pull requests.

For interview preparation, see [docs/ci-cd-interview-guide.md](C:/dev/healthcare-platform/docs/ci-cd-interview-guide.md).
