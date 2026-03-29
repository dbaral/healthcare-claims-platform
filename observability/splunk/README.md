# Splunk

This setup runs a local Splunk Enterprise container and monitors the healthcare platform log files from `observability/logs`.

## Start Splunk

From the repository root:

```powershell
docker compose --profile splunk up -d splunk
```

## Splunk Web

- URL: `http://localhost:8000`
- Username: `admin`
- Password: `${SPLUNK_PASSWORD}` or the local default from compose

## How log ingestion works

1. Each Spring service writes logs to `observability/logs/<service>.log`
2. The Splunk container mounts that folder at `/var/log/healthcare`
3. The app config in `observability/splunk/apps/healthcare_logs/local/inputs.conf` monitors those files
4. Events land in the `main` index with sourcetype `healthcare:service`

## Useful searches

```spl
index=main sourcetype=healthcare:service
| stats count by source
```

```spl
index=main sourcetype=healthcare:service ERROR
| stats count by source
```

```spl
index=main sourcetype=healthcare:service source=claim-validation-service
| search failed OR exception OR error
```

## Notes

- Splunk only sees logs after the services start writing to the monitored files.
- If a service is run with a different working directory, set `LOG_FILE_PATH` explicitly so it still writes into `observability/logs`.
