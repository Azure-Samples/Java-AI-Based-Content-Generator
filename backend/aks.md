# Deploying Backend Application to Azure Kubernetes Service (AKS)
This guide covers the steps to build, push, and deploy a Java application to Azure Kubernetes Service (AKS) using the CLI.

## Prerequisites

* Azure CLI installed and logged in
* Azure Kubernetes Service (AKS) and Azure Container Registry (ACR) set up
* Maven installed
* Docker installed


## Step 1: Build the Java Application
First, build the application using Maven:

```bash
./mvnw clean package
```
This command will generate a WAR file in the `target` directory.

## Step 2: Set Up AKS and ACR (Optional)
If you don't already have an Azure Kubernetes Service (AKS) and Azure Container Registry (ACR) set up, you can follow this optional step to create them.

### Create AKS Cluster and ACR

To create an AKS cluster and ACR, use the following commands:

1. **Create a resource group:**
   ```bash
   az group create --name <YOUR_RESOURCE_GROUP> --location <LOCATION>
   ```

2. **Create an ACR:**
   ```bash
   az acr create --resource-group <YOUR_RESOURCE_GROUP> --name <YOUR_ACR_NAME> --sku Basic
   ```

3. **Create an AKS cluster:**
   ```bash
   az aks create --resource-group <YOUR_RESOURCE_GROUP> --name <YOUR_AKS_CLUSTER_NAME> --node-count 1 --enable-managed-identity --attach-acr <YOUR_ACR_NAME>
   ```
Once the AKS cluster and ACR are set up, proceed with the next steps.

## Step 3: Build and Push Docker Image

### Update Environment Variables in Dockerfile

Before building the Docker image, make sure that the environment variables in your [Dockerfile](Dockerfile) are up to date. These variables are critical for your application to interact with Azure services like Cosmos DB, Blob Storage, and MongoDB.

[**_Reference_**](env_variables.md)

### Build Docker Image
To build the Docker image, use the following command:

```bash
docker build --build-arg AZURE_KEYVAULT_URI=http://your-key-vault-url/ \
             --build-arg AZURE_CLIENT_ID=your-client-id \
             --build-arg AZURE_CLIENT_SECRET=your-client-secret \
             --build-arg AZURE_TENANT_ID=your-tenant-id \
             -t <YOUR_ACR_NAME>.azurecr.io/backend-image:v1 .
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
docker push <YOUR_ACR_NAME>.azurecr.io/backend-image:v1
```

## Step 4: Run Docker Image Locally (Optional)
If you want to test the Docker image locally, use the following commands:

### Build Docker Image Locally
```bash
docker build -t cg-backend .
```
### Run Docker Image Locally
```bash
docker run -p 8080:8080 cg-backend
```
You can access the application at **http://localhost:8080**.

## Step 5: Deploy to AKS
### Apply Kubernetes Deployment Locally
First, apply the Kubernetes deployment using the following command:

```bash
kubectl apply -f backend-deployment.yml
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
kubectl apply -f backend-deployment.yml
```
This will deploy your application to the AKS cluster.

## Step 6: Destroy Resources (Optional)

If you want to clean up and destroy the resources after you're done, you can follow these steps:

1. **Delete AKS Cluster**
   To delete the AKS cluster, use the following command:
   ```bash 
   az aks delete --name <YOUR_AKS_CLUSTER_NAME> --resource-group <YOUR_RESOURCE_GROUP> --yes --no-wait
   ```
2. **Delete ACR (Azure Container Registry)**
   To delete the ACR, run this command:
   ```bash 
   az acr delete --name <YOUR_ACR_NAME> --resource-group <YOUR_RESOURCE_GROUP>
   ```
3. **Delete Resource Group**
   To delete the resource group, which will also delete all associated resources, use the following command:
   ```bash 
   az group delete --name <YOUR_RESOURCE_GROUP> --yes --no-wait
   ```
This will completely remove your AKS cluster, ACR, and all resources associated with the resource group.
With these additional instructions, users will have the option to destroy all resources created for the deployment, ensuring a clean environment if needed.
