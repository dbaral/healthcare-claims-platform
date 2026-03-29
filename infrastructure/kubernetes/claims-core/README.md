# Kubernetes Starter Manifests

This folder contains starter Kubernetes manifests for the core claim workflow:

- Zookeeper
- Kafka
- PostgreSQL for claim intake
- PostgreSQL for claim validation
- PostgreSQL for payment
- PostgreSQL for notification
- PostgreSQL for audit
- `claim-intake-service`
- `claim-validation-service`
- `eligibility-service`
- `fraud-check-service`
- `adjudication-service`
- `payment-service`
- `notification-service`
- `audit-service`

## Apply Order

```powershell
kubectl apply -f infrastructure/kubernetes/claims-core/namespace.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/configmap.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/secret.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/zookeeper.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/kafka.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/claims-postgres.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/claim-validation-postgres.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/payment-postgres.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/notification-postgres.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/audit-postgres.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/claim-intake-service.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/claim-validation-service.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/eligibility-service.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/fraud-check-service.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/adjudication-service.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/payment-service.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/notification-service.yaml
kubectl apply -f infrastructure/kubernetes/claims-core/audit-service.yaml
```

## Important Note

These manifests are a learning-friendly starter deployment. For a real deployment, the next step would be to:

- add persistent volumes
- add health probes
- add ingress
- add image registry references instead of local image names
- create Kafka topics explicitly or use startup automation
