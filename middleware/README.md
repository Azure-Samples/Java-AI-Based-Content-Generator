# Marketing Content Generator Backend API

This is a backend API for generating marketing content, built using Spring Boot and integrated with Azure OpenAI.

## Features

- Generate marketing content using Azure OpenAI GPT models.
- Backend API for content generation, customizable for different marketing needs.

## Prerequisites

- Java 17
- Maven
- Azure account with OpenAI deployed
- Azure OpenAI API Key and Endpoint

## Getting Started

1. **Clone the Repository**

   ```bash
   git clone https://github.com/terawe/contentgenerator.git
   cd middleware
   ```
   
   ## Setup Environment Variables - [Reference](env_variables.md)

2. **Configure Azure OpenAI**

    * Go to [Azure OpenAI](https://ai.azure.com) and deploy the GPT model.
    * Retrieve your **Azure OpenAI Endpoint URL** from your OpenAI service.
    * Retrieve your **Azure OpenAI API Key** from the Azure portal.

3. **Update Application Properties**
   
   Open the `application.properties` file ( path [src/main/resources/application.properties](src/main/resources/application.properties)) and update the following values:
   
    ```properties
   azure.openai.endpoint_url=${AZURE_OPENAI_ENDPOINT_URL}
   azure.openai.key=${AZURE_OPENAI_KEY}
   ```

4. **Run the Application**

   Use Maven to run the application:

    ```bash
    ./mvnw spring-boot:run
   ```

5. **Build the Application**

   Use Maven to run the application:

    ```bash
    ./mvnw clean package
   ```
## Deployment Methods

### 1. Deploying to Azure Kubernetes Service (AKS)

You can deploy the application to an Azure Kubernetes Service (AKS) cluster. Detailed instructions are provided in the [AKS Deployment Guide](aks.md).

- **API Exposure**: Once deployed, the API should be exposed via Azure API Management. For more information, refer to the [API Management Guide](api_management.md).

### 2. Deploying to Azure App Service

Alternatively, you can deploy the application to Azure App Service. Follow the steps outlined in the [App Service Deployment Guide](app_service.md).

- **API Exposure**: Similar to the AKS deployment, the API for the App Service deployment should be exposed via Azure API Management. Details can be found in the [API Management Guide](api_management.md).

## API Management

Azure API Management provides a unified front-end for your application’s APIs. Regardless of whether you deploy to AKS or App Service, using Azure API Management allows you to:

- Securely expose your APIs
- Monitor and analyze API usage
- Apply policies for rate limiting, authorization, etc.

More details can be found in the [API Management Guide](../api_management.md).