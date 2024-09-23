# Backend Service

## Overview
The Backend Service provides the following APIs:
- **Customer API**: Retrieve customer data.
- **Product API**: Retrieve product details.
- **Product Vector API**: Retrieve a list of similar products using vector-based search.

The backend connects to Azure Blob Storage and Azure Cosmos MongoDB using Key Vault references with Managed Identity.

## Prerequisites
- Java 21
- Maven
- Azure Key Vault with necessary secrets
- Managed Identity configured in Azure

## Local Setup Instructions
1. **Clone the repository**:
    ```bash
    git clone https://github.com/Azure-Samples/Java-AI-Based-Content-Generator
    cd Java-AI-Based-Content-Generator/backend
    ```
2. **Key Vault Setup**:

   Before adding or accessing secrets in Key Vault, you need to follow the setup instructions in the [Azure Key Vault Setup Guide](../key_vault_setup.md), which include:
    * Assigning the necessary **Key Vault Administrator** and **Key Vault Reader** roles.
    * Adding the required secrets for the backend service.

3. **Environment Variables for Local Development:**

    * Set the required Azure environment variables to access secrets from Key Vault:
   ```bash
   export AZURE_KEYVAULT_URL=<your_keyvault_url>
   ```

4. **Run the Application**:
    * Start the backend service using the following command:

      **Install dependencies**:
      ```bash
      ./mvnw clean install
      ```
      **Run the application**:
      ```bash
      ./mvnw spring-boot:run
      ```

   The service will now be running at `http://localhost:8080`.

## Key Vault Secrets
The middleware service requires the following secrets in **Azure Key Vault**:

- `MiddlewareServiceBaseUrl`: `http://localhost:8081` (for local), or middleware APIM URL (for deployment)
- `MiddlewareServiceProductEmbeddingEndpoint`: `/api/v1/generate/embedding` (for local), or the middleware APIM endpoint
- `AzureCosmosConnectionString`
- `MongoDBDatabaseName`
- `AzureStorageConnectionString`
- `StorageContainerName`

## Deployment
- For Azure App Service deployment, see [app_service.md](./app_service.md).
- For AKS deployment, see [aks.md](./aks.md).
