# Spring Boot Application

This Spring Boot application interacts with Azure Cosmos MongoDB to store critical data and Azure Blob Storage for media storage. It appends data to MongoDB based on a configuration and performs various file operations with Azure Blob Storage.

## Prerequisites

Before running the application, ensure that you have the following dependencies set up:

- Java 21
- Spring Boot
- MongoDB (Azure Cosmos DB with MongoDB API)
- Azure Blob Storage

## Getting Started

### Clone the Repository**

   ```bash
   git clone https://github.com/terawe/contentgenerator.git
   cd backend
   ```

## Configuration - [Reference](env_variables.md)

The application requires certain configurations to be set in the `application.properties` file ( path [src/main/resources/application.properties](src/main/resources/application.properties)).

**Azure Key Vault Setup and App Registration**
* Create Azure Key Vault and Set Secrets
  * **Step 1: Create an Azure Key Vault**
    * Go to the Azure Portal.
    * Navigate to "Create a resource" and search for "Key Vault".
    * Click "Create" and fill in the required details:
     ```
      Name: Your Key Vault name (e.g., myKeyVault)
      Subscription: Choose your subscription
      Resource Group: Create or select an existing resource group
      Region: Choose the region where you want to deploy the Key Vault
     ```
    * Click "Review + create" and then "Create".
  * **Step 2: Set Secrets in Azure Key Vault**
    * Navigate to your Key Vault in the Azure Portal.
    * Go to the "Secrets" section and click "Generate/Import".
    * Enter the name and value for each secret. Use the constants listed below for reference:
         ```
            MiddlewareServiceBaseUrl
            MiddlewareServiceProductEmbeddingEndpoint
            MiddlewareServiceAccessKey
            AzureCosmosConnectionString
            MongoDBDatabaseName
            AzureStorageConnectionString
            StorageContainerName
         ```
    * Click "Create" to add each secret.

### 1. MongoDB Connection

You need to specify the connection string to your Azure Cosmos DB (with MongoDB API).

#### How to Get Azure Cosmos MongoDB Connection String

1. Go to your Azure Portal.
2. Navigate to your Azure Cosmos DB resource.
3. Under the **Settings** section, click on **Connection String**.
4. Copy the **Primary Connection String** and add it in the key vault.

### 2. Azure Blob Storage Connection

Similarly, you need to add the connection string for your Azure Storage account.

#### How to Get Azure Storage Account Connection String

1. Go to your Azure Portal.
2. Navigate to your Storage Account resource.
3. Under the **Security + Networking** section, click on **Access keys**.
4. Copy the **Connection String** for either key1 or key2 and add it in the key vault.

### 3. Data Append Configuration

The application has a feature to append data to MongoDB upon startup if a specific property is set to `true`. Add the following configuration:

```properties
data.append.enabled=true
```
* When `data.append.enabled=true`, the application will append data to MongoDB upon startup.
* If set to `false`, no data will be appended.

### 5. **Azure Managed Identity Setup** - [Reference](env_variables.md)

### 4. Running the Application
Once you have added the required configurations in your `application.properties`, you can run the application using the following command:

```bash
./mvnw spring-boot:run
```

### 5. Build the Application
Once you have added the required configurations in your `application.properties`, you can run the application using the following command:

```bash
./mvnw clean package
```

### Features

* **MongoDB Integration**: Stores and retrieves data from Azure Cosmos DB (MongoDB API).
* **Azure Blob Storage**: Handles file uploads and retrievals from Azure Blob Storage.
* **Data Append**: Automatically appends data to MongoDB on startup if the `data.append.enabled` property is `true`.

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
