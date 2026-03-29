# Observability

This repository includes a local observability stack for the healthcare platform based on Prometheus, Grafana, and Datadog.

## What it covers

- Prometheus scrapes the Spring Boot actuator Prometheus endpoints for every service.
- Grafana comes pre-provisioned with Prometheus as a datasource and a starter dashboard.
- Datadog is optional and uses the OpenMetrics integration to scrape the same service endpoints.
- Splunk is optional and monitors local service log files from `observability/logs`.

## Service ports monitored

- `clinic-soap-service`: `8080`
- `patient-rest-service`: `8081`
- `claim-intake-service`: `8091`
- `claim-validation-service`: `8092`
- `eligibility-service`: `8093`
- `fraud-check-service`: `8094`
- `adjudication-service`: `8095`
- `payment-service`: `8096`
- `notification-service`: `8097`
- `audit-service`: `8098`

## Start Prometheus and Grafana

From the repository root:

```powershell
docker compose up -d prometheus grafana
```

## Start Datadog

Datadog is behind a compose profile so local development does not require an API key by default.

```powershell
$env:DD_API_KEY = "your_api_key"
$env:DD_SITE = "datadoghq.com"
$env:DD_ENV = "local"
docker compose --profile datadog up -d datadog-agent
```

## URLs

- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000`
  - username: `admin`
  - password: `admin`

## Start Splunk

```powershell
docker compose --profile splunk up -d splunk
```

Then open:

- Splunk: `http://localhost:8000`
  - username: `admin`
  - password: `${SPLUNK_PASSWORD}` or `LocalSplunk123!` by default

## Notes

- The monitoring containers scrape application endpoints on `host.docker.internal`, so the Spring Boot services should be running on the host machine.
- If only some services are running, the dashboards still work, and the stopped services simply show up as unavailable.
- The Datadog OpenMetrics config is meant for local learning and validation. If you use it in a shared Datadog account, narrow the metric patterns first to control custom-metric volume.
- Splunk reads log files rather than scraping metrics. Service log files are configured under `observability/logs`.
