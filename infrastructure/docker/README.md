# Docker Build Guide

This folder documents how to build container images for the core healthcare claims services.

## Build Images

From the repository root:

```powershell
docker build -t healthcare/claim-intake-service:latest services/claim-intake-service
docker build -t healthcare/claim-validation-service:latest services/claim-validation-service
docker build -t healthcare/eligibility-service:latest services/eligibility-service
docker build -t healthcare/fraud-check-service:latest services/fraud-check-service
docker build -t healthcare/adjudication-service:latest services/adjudication-service
docker build -t healthcare/payment-service:latest services/payment-service
docker build -t healthcare/notification-service:latest services/notification-service
docker build -t healthcare/audit-service:latest services/audit-service
```

## Run Images Locally

These images expect Kafka and PostgreSQL to be reachable through environment variables.

Example for `claim-intake-service`:

```powershell
docker run --rm -p 8091:8091 `
  -e DB_HOST=host.docker.internal `
  -e DB_PORT=5433 `
  -e DB_NAME=claimsdb `
  -e DB_USERNAME=claims `
  -e DB_PASSWORD=claimspw `
  -e KAFKA_BOOTSTRAP_SERVERS=host.docker.internal:9092 `
  healthcare/claim-intake-service:latest
```

## Why This Matters

- Docker image = packaged application
- Kubernetes deploys containers from images
- Same image can run in local, test, and cluster environments
