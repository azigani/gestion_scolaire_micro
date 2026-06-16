# Kubernetes Deployment for EduManager

This directory contains Kubernetes manifests for deploying the EduManager microservices architecture.

## Prerequisites

- Kubernetes cluster (v1.28+)
- kubectl configured
- Docker registry access

## Deployment Steps

1. **Create namespace:**
   ```bash
   kubectl apply -f namespace.yaml
   ```

2. **Create ConfigMap:**
   ```bash
   kubectl apply -f configmap.yaml
   ```

3. **Create Secrets:**
   ```bash
   kubectl apply -f secret.yaml
   ```
   Note: Update the secret.yaml with your actual base64-encoded secrets.

4. **Deploy microservices:**
   ```bash
   kubectl apply -f tenant-deployment.yaml
   kubectl apply -f enrollment-deployment.yaml
   kubectl apply -f academic-deployment.yaml
   kubectl apply -f financial-deployment.yaml
   kubectl apply -f hr-deployment.yaml
   kubectl apply -f communication-deployment.yaml
   kubectl apply -f gateway-deployment.yaml
   kubectl apply -f frontend-deployment.yaml
   ```

5. **Configure autoscaling:**
   ```bash
   kubectl apply -f horizontal-pod-autoscaler.yaml
   ```

6. **Verify deployment:**
   ```bash
   kubectl get pods -n edumanager
   kubectl get services -n edumanager
   ```

## Services

- **edumanager-tenant**: Port 8081
- **edumanager-enrollment**: Port 8082
- **edumanager-academic**: Port 8083
- **edumanager-financial**: Port 8084
- **edumanager-hr**: Port 8085
- **edumanager-communication**: Port 8086
- **edumanager-api-gateway**: Port 8080 (LoadBalancer)
- **edumanager-frontend**: Port 80 (LoadBalancer)

## Scaling

The Horizontal Pod Autoscaler is configured for:
- **tenant**: 2-10 replicas
- **api-gateway**: 2-10 replicas

Add HPA for other services as needed.

## Monitoring

- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000
- Jaeger: http://localhost:16686
