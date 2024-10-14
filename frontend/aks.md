# Deploying Frontend Application to Azure Kubernetes Service (AKS)
This guide covers the steps to build, push, and deploy a React application to Azure Kubernetes Service (AKS) using the CLI.

## Prerequisites

* Azure CLI installed and logged in
* Azure Kubernetes Service (AKS) and Azure Container Registry (ACR) set up
* Docker installed
* kubectl installed
* NodeJS installed


## Reference - [AKS](../aks.md)


## Build and Push Docker Image

### Update Environment Variables in Dockerfile

Before building the Docker image, make sure that the environment variables in your [Dockerfile](Dockerfile) are up to date. These variables are critical for your application to interact with Azure services like Cosmos DB, Blob Storage, and MongoDB.

[**_Reference_**](env_variables.md)
[**_env_**](.env.example)

### Build Docker Image
To build the Docker image, use the following command:

```bash
docker build -t <ACR_NAME>.azurecr.io/aistudy/frontend:latest .
```

### Login to Azure Container Registry (ACR)
Before pushing the Docker image to ACR, you need to log in using your ACR credentials:

1. **Retrieve ACR credentials**:

    ```bash
    az acr credential show --name <ACR_NAME>
    ```

2. **Log in to ACR**:

    ```bash
    docker login <ACR_NAME>.azurecr.io
    ```


### Push Docker Image
Push the Docker image to your ACR:

```bash
docker push <YOUR_ACR_NAME>.azurecr.io/aistudy/frontend:latest
```

## Run Docker Image Locally (Optional)
If you want to test the Docker image locally, use the following commands:


### Run Docker Image Locally
```bash
docker run -p 80:80 <YOUR_ACR_NAME>.azurecr.io/aistudy/frontend:latest
```
You can access the application at **http://localhost**.

## Deploy to AKS

### Update Variables in [`frontend-deployment.yml`](frontend-deployment.yml)

Before run the Docker image in K8S, make sure that the environment variables in your [`frontend-deployment.yml`](frontend-deployment.yml) are up to date. These variables are critical for your application to interact with Azure services like ACR_NAME, MIDDLEWARE_SERVICE_BASE_URL, APP_CLIENT_ID, CONTENT_GENERATOR_ENDPOINT, MIDDLEWARE_SERVICE_ACCESS_KEY and AZURE_KEYVAULT_URI (refer [env](.env.example)).

### Apply Kubernetes Deployment Locally
First, apply the Kubernetes deployment using the following command:

```bash
kubectl apply -f frontend-deployment.yml
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
   az aks get-credentials --resource-group <YOUR_RESOURCE_GROUP> --name <AKS_NAME>
   ```

```bash
kubectl apply -f frontend-deployment.yml
```
This will deploy your application to the AKS cluster.
