# Deploying Frontend Application to Azure Kubernetes Service (AKS)
This guide covers the steps to build, push, and deploy a React application to Azure Kubernetes Service (AKS) using the CLI.

## Prerequisites

* Azure CLI installed and logged in
* Azure Kubernetes Service (AKS) and Azure Container Registry (ACR) set up
* Docker installed
* kubectl installed
* NodeJS installed


## Step 1: Build the React Application
First, build the React application using `npm` or `yarn`. This will generate a production-ready build of your app.

```bash
npm run build
```
This command will generate a `build` directory containing the static files of your React application.

## Step 2: Build and Push Docker Image

### Update Environment Variables in Dockerfile

Before building the Docker image, make sure that the environment variables in your [Dockerfile](Dockerfile) are up to date. These variables are critical for your application to interact with Azure services like Cosmos DB, Blob Storage, and MongoDB.

[**_Reference_**](env_variables.md)

### Build Docker Image
To build the Docker image, use the following command:

```bash
docker build --build-arg REACT_APP_SERVICE_BASE_URL=http://your-service-base-url \
             --build-arg REACT_APP_CLIENT_ID=your-client-id \
             --build-arg REACT_APP_CONTENT_GENERATOR_ENDPOINT=/api/v1/generate/content \
             --build-arg REACT_APP_SERVICE_ACCESS_KEY=your-access-key \
             -t <YOUR_ACR_NAME>.azurecr.io/frontend-image:v1 .
```

### Login to Azure Container Registry (ACR)
Before pushing the Docker image to ACR, you need to log in using your ACR credentials:

1. **Retrieve ACR credentials**:

    ```bash
    az acr credential show --name <YOUR_ACR_NAME>
    ```

2. **Log in to ACR**:

    ```bash
    docker login <YOUR_ACR_NAME>.azurecr.io
    ```


### Push Docker Image
Push the Docker image to your ACR:

```bash
docker push <YOUR_ACR_NAME>.azurecr.io/frontend-image:v1
```

## Step 3: Run Docker Image Locally (Optional)
If you want to test the Docker image locally, use the following commands:

### Build Docker Image Locally
```bash
docker build -t cg-frontend .
```
### Run Docker Image Locally
```bash
docker run -p 80:80 cg-frontend
```
You can access the application at **http://localhost:80**.

## Step 4: Deploy to AKS
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
   az aks get-credentials --resource-group <YOUR_RESOURCE_GROUP> --name <YOUR_AKS_CLUSTER_NAME>
   ```

```bash
kubectl apply -f frontend-deployment.yml
```
This will deploy your application to the AKS cluster.