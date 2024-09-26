# Middleware Service

## Overview
The Middleware Service provides content generation and embedding vector APIs. It connects to OpenAI services for processing user queries and exposes these APIs via Azure API Management (APIM) in production.

## Prerequisites for Local Development
- Java 17 or higher
- Maven
- Access to Azure OpenAI and Azure Key Vault
- Backend service running locally on port 8080

## Local Setup Instructions
1. **Clone the repository**:
    ```bash
    git clone https://github.com/Azure-Samples/Java-AI-Based-Content-Generator
    cd Java-AI-Based-Content-Generator/middleware
    ```

2. **Configure the Application Properties**:
   To avoid port conflicts, ensure the `server.port` is set to `8081` in the [`application.properties`](./src/main/resources/application.properties) file:
   ```properties
   server.port=8081
   ```

3. **Key Vault Setup**:

   Before adding or accessing secrets in Key Vault, you need to follow the setup instructions in the [Azure Key Vault Setup Guide](../key_vault_setup.md), which include:
   * Assigning the necessary **Key Vault Administrator** and **Key Vault Reader** roles.
   * Adding the required secrets for the middleware service.

4. **Environment Variables for Local Development:**

   * Set the required Azure environment variables to access secrets from Key Vault:
   ```bash
   export AZURE_KEYVAULT_URL=<your_keyvault_url>
   ```

5. **Run the Application**:
    * [Dependency summary](./dependencies-summary.md)
    * Start the backend service using the following command:
        * **Install dependencies**:
          ```bash
          ./mvnw clean install
          ```
        * **Run the application**:
          ```bash
          ./mvnw spring-boot:run
          ```

   The service will now be running at `http://localhost:8081`.


## Key Vault Secrets
The middleware service requires the following secrets in **Azure Key Vault**:

- `BackendServiceBaseUrl`: `http://localhost:8080` (for local), or backend APIM URL (for deployment)
- `BackendServiceProductEndpoint`: `/api/v1/product` (for local), or backend APIM endpoint
- `BackendServiceSimilarProductEndpoint`: `/api/v1/product/similar` (for local), or backend APIM endpoint
- `AzureOpenAiEndpointUrl` (OpenAI GPT-4o completion model **Target URI**)
- `AzureOpenAiAccessKey` (OpenAI GPT-4o **Key**)
- `AzureOpenAiEmbeddingEndpointUrl` (OpenAI text-embedding-3-small model **Target URI**)
- `AzureOpenAiEmbeddingKey` (OpenAI embedding model **Key**)

## Deployment
- For Azure App Service deployment, see [app_service.md](./app_service.md).
- For AKS deployment, see [aks.md](./aks.md).
