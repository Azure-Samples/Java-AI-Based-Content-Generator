# Deploying Middleware Application to Azure Kubernetes Service (AKS)
This guide covers the steps to build, push, and deploy a Java application to Azure Kubernetes Service (AKS) using the CLI.

## Prerequisites

* Azure CLI installed and logged in
* Azure Kubernetes Service (AKS) and Azure Container Registry (ACR) set up
* Maven installed
* Docker installed

## Reference - [AKS](../aks.md)

## Build and Push Docker Image

### Update Environment Variables in Dockerfile

Before building the Docker image, make sure that the environment variables in your [Dockerfile](Dockerfile) are up to date. These variables are critical for your application to interact with Azure services like Cosmos DB, Blob Storage, and MongoDB.

[**_Reference_**](env_variables.md)

### Build Docker Image
To build the Docker image, use the following command:

```bash
# Build docker image
docker build -t <ACR_Name>.azurecr.io/aistudy/middleware-service:latest .

```

### Push Docker Image
Push the Docker image to your ACR:

```bash
docker push <ACR_Name>.azurecr.io/middleware-service:latest
```

## Run Docker Image Locally (Optional)
If you want to test the Docker image locally, use the following commands:


### Run Docker Image Locally
```bash
docker run -p 8081:8080 <ACR_Name>.azurecr.io/aistudy/middleware-service:latest
```
You can access the application at **http://localhost:8081**.

## Deploy to AKS

### Update Variables in [`middleware-deployment.yml`](middleware-deployment.yml)

Before run the Docker image in K8S, make sure that the environment variables in your [`middleware-deployment.yml`](middleware-deployment.yml) are up to date. These variables are critical for your application to interact with Azure services like ACR_NAME and AZURE_KEYVAULT_URI.

### Apply Kubernetes Deployment Locally
First, apply the Kubernetes deployment using the following command:

```bash
kubectl apply -f middleware-deployment.yml
```
### Push Kubernetes Deployment to AKS
To deploy the application to AKS, ensure your kubectl is connected to your AKS cluster, then apply the deployment:

1. **Log in to Azure**
   Log in to your Azure account using the Azure CLI:
    ```bash
   az login
    ```
2. **AKS Login**
   ```bash
    az aks get-credentials --resource-group <RESOURCE_GROUP_NAME> --name <AKS_NAME>
   ```
## Deploy to AKS
```bash
kubectl apply -f middleware-deployment.yml
```
This will deploy your application to the AKS cluster.
## Check the Status of Deployments

To check the status of the deployments, use the following command:

```bash
kubectl get deployments
kubectl get services 
```
This will show you the status of the deployments and services in your AKS cluster.
