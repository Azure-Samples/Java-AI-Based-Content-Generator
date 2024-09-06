# Spring Boot Application

This Spring Boot application interacts with Azure Cosmos MongoDB to store critical data and Azure Blob Storage for media storage. It appends data to MongoDB based on a configuration and performs various file operations with Azure Blob Storage.

## Prerequisites

Before running the application, ensure that you have the following dependencies set up:

- Java 17
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

### 1. MongoDB Connection

You need to specify the connection string to your Azure Cosmos DB (with MongoDB API).

Add the following property in your `application.properties` file :

```properties
spring.data.mongodb.uri=${AZURE_COSMOS_MONGODB_CONNECTION_STRING}
```

#### How to Get Azure Cosmos MongoDB Connection String

1. Go to your Azure Portal.
2. Navigate to your Azure Cosmos DB resource.
3. Under the **Settings** section, click on **Connection String**.
4. Copy the **Primary Connection String** and add `AZURE_COSMOS_MONGODB_CONNECTION_STRING` in environment variables.

### 2. Azure Blob Storage Connection

Similarly, you need to add the connection string for your Azure Storage account.

```properties
azure.storage.connection-string=${AZURE_STORAGE_CONNECTION_STRING}
```

#### How to Get Azure Storage Account Connection String

1. Go to your Azure Portal.
2. Navigate to your Storage Account resource.
3. Under the **Security + Networking** section, click on **Access keys**.
4. Copy the **Connection String** for either key1 or key2 and add `AZURE_STORAGE_CONNECTION_STRING` in environment variables.

### 3. Data Append Configuration

The application has a feature to append data to MongoDB upon startup if a specific property is set to `true`. Add the following configuration:

```properties
data.append.enabled=true
```
* When `data.append.enabled=true`, the application will append data to MongoDB upon startup.
* If set to `false`, no data will be appended.

### Running the Application
Once you have added the required configurations in your `application.properties`, you can run the application using the following command:

```bash
./mvnw spring-boot:run
```

### Build the Application
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
