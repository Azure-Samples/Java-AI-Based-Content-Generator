# Azure DevOps CI/CD Pipeline for Azure App Service and AKS Deployment

This document provides a setup reference for configuring CI/CD pipelines using Azure DevOps for a **Java Spring Boot backend**, **Java Spring Boot middleware**, and a **ReactJS frontend**. The pipeline will build and deploy these applications to **Azure App Service** and **Azure Kubernetes Service (AKS)**.

The YAML file [(`azure-pipelines.yml`)](azure-pipelines.yml) located at the root of the repository contains the pipeline configuration and is structured as follows:

## Pipeline Overview

The pipeline includes:

1. **Build Stage**: Builds each application (`backend`, `middleware`, and `frontend`) in parallel, producing artifacts for deployment.
2. **Deploy Stage**: Deploys the artifacts to Azure App Service and AKS using service connections.

## Pipeline Configuration Explanation

### Variable Groups

In the pipeline, we utilize variable groups to manage environment-specific values and secrets:

![VG_List.png](images/VG_List.png)

```yaml
variables:
  - group: ai-study-vg
  - group: ai-study-secrets
```
* `ai-study-vg`: Contains environment variables such as Azure Subscription ID, Resource Group, and Application Names.
![VG.png](images/VG.png)
* `ai-study-secrets`: Retrieves secrets from Azure Key Vault, such as API keys and service base URLs, ensuring sensitive information is not hard-coded in the pipeline.
![VG_KV_secret.png](images/VG_KV_secret.png)
![VG_KV_secret.png](images/VG_KV_secret.png)

![PipelinePermission.png](images/PipelinePermission.png)

### Build Stage
Each of the services (`backend`, `middleware`, and `frontend`) is built in parallel:

```yaml
jobs:
  - job: Build_Backend
    displayName: 'Build Backend Service'
    steps:
      - task: Maven@4
        inputs:
          mavenPomFile: 'backend/pom.xml'
          goals: 'clean package'

```
* The `Maven@4` task compiles and packages the Java application from the `backend/pom.xml` file.
* Artifacts are published for later use in the deployment stage.

### Deploy Stage
The deployment stage consists of jobs for each application, deploying to either Azure App Service or AKS:

```yaml
jobs:
  - job: Deploy_Backend_AppService
    displayName: 'Deploy Backend to Azure App Service'
    steps:
      - task: AzureWebApp@1
        inputs:
          azureSubscription: '$(AzureSubscription)'
          appName: '$(BackendAppServiceName)'
          package: '$(Pipeline.Workspace)/backend/backend.war'
```

* The `AzureWebApp@1` task is used to deploy the `backend.war` artifact to the specified Azure App Service.
* Variables such as `$(AzureSubscription)` and `$(BackendAppServiceName)` are retrieved from the variable groups defined earlier.

### Explanation of the AzureWebApp@1 Task
The `AzureWebApp@1` task in Azure DevOps is used to deploy applications to Azure App Service. It requires the following inputs:

`azureSubscription`: The service connection name to authenticate against Azure.
`appName`: The name of the Azure Web App to which the package will be deployed.
`package`: The path to the artifact (e.g., `backend.war`) that will be deployed.
`appType`: The type of Azure App Service (e.g., `webAppLinux`).

### Reference for Azure Service Connection
For this pipeline to work, you must have a service connection established between Azure DevOps and Azure. You can follow the [Azure Service Connection](https://learn.microsoft.com/en-us/azure/devops/pipelines/library/service-endpoints?view=azure-devops) documentation to set up this connection.

### Deploying to Azure Kubernetes Service (AKS)
For AKS deployments, Docker images are built from the application code and pushed to Azure Container Registry (ACR). The Kubernetes manifests (`*-deployment.yml`) are applied using `kubectl` commands.

```yaml
jobs:
  - job: Deploy_Backend_AKS
    displayName: 'Deploy Backend to AKS'
    steps:
      - script: |
          az aks get-credentials --resource-group $(ResourceGroup) --name $(AKSClusterName)
          docker build -t $(AcrName).azurecr.io/aistudy/backend:latest backend/
          docker push $(AcrName).azurecr.io/aistudy/backend:latest
          kubectl apply -f backend/backend-deployment.yml

```

* `az aks get-credentials`: Fetches the Kubernetes credentials for the specified AKS cluster.
* `docker build` and `docker push`: Builds and pushes Docker images to the ACR.
* `kubectl apply`: Deploys the updated image to AKS using the provided deployment file.

### GitHub Repository Integration
To integrate a GitHub repository in Azure DevOps, navigate to **Project Settings > Repos > GitHub Connections** and add your GitHub repository. This allows you to trigger the pipeline automatically on code changes.

 ![ServiceConnection.png](images/ServiceConnection.png)
![Github.png](images/Github.png)

### Testing the Pipeline
After configuring the pipeline, run it manually or trigger it by committing code changes to your GitHub repository. Check the pipeline logs for build and deployment success, and validate the application is live in Azure App Service or AKS.

![pipeline.png](images/pipeline.png)
![pipeline_list.png](images/pipeline_list.png)
![pipeline_summary.png](images/pipeline_summary.png)
![pipeline_detail.png](images/pipeline_detail.png)


### Conclusion
By following this setup, you now have a production-grade CI/CD pipeline that automates the build and deployment process for multiple services to Azure App Service and AKS. This setup ensures consistency and reduces manual intervention, streamlining the development workflow.