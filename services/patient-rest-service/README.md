# Patient REST Service

A simple Spring Boot REST API with a scalable architecture that fits this repository's service layout.

## Architecture

The service uses a layered structure so features can grow without mixing HTTP, business, and persistence concerns:

- `api`: controllers, request models, response models, exception mapping
- `application`: use-case orchestration and business rules
- `domain`: core entities and repository contracts
- `infrastructure`: database adapters and framework-specific integrations

## Endpoints

- `POST /api/v1/patients`
- `GET /api/v1/patients`
- `GET /api/v1/patients/{id}`
- `PUT /api/v1/patients/{id}`
- `DELETE /api/v1/patients/{id}`
- `GET /actuator/health`
- `GET /actuator/prometheus`
- `GET /swagger-ui.html`
- `GET /v3/api-docs`

## Run

Start the patient database and Kafka stack from the repository root:

```bash
docker compose up -d patient-mysql zookeeper kafka kafka-ui
```

Then run the service:

```bash
mvn spring-boot:run
```

The app starts on port `8081` and uses MySQL by default.

Swagger UI is available at `http://localhost:8081/swagger-ui.html`.
Kafka UI is available at `http://localhost:8085`.

## Kafka

The service publishes patient lifecycle events to the `patient.events` topic whenever a patient is created, updated, or deleted.

Default Kafka connection values used by the service:

- bootstrap servers: `localhost:9092`
- topic: `patient.events`

Create or update a patient through the REST API, then open Kafka UI to inspect the messages.

## Database

Default connection values used by the service:

- host: `localhost`
- port: `3307`
- database: `patientdb`
- username: `patient`
- password: `patientpw`
