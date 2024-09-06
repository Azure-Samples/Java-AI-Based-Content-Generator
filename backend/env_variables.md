# Environment Variables for Backend Service

When deploying your application to Azure Web App Service, it's important to configure environment variables that your application will use to connect to various services. Below is a table listing common environment variables used for connecting to Azure Cosmos DB, Azure Storage, and MongoDB.

| **Environment Variable**                     | **Description**                                                                                               |
|----------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| `AZURE_COSMOS_MONGODB_CONNECTION_STRING`     | The connection string for your Azure Cosmos DB instance configured to work with MongoDB API.                   |
| `AZURE_STORAGE_CONNECTION_STRING`            | The connection string for your Azure Storage account, used for accessing storage services like Blob Storage.   |
| `MONGODB_DATABASE_NAME`                      | The name of the MongoDB database your application will connect to within Azure Cosmos DB or a standalone MongoDB.|
| `AZURE_STORAGE_CONTAINER_NAME`               | The name of the container within Azure Blob Storage where your application's data will be stored.              |

## Explanation of Environment Variables

### `AZURE_COSMOS_MONGODB_CONNECTION_STRING`
- **Purpose**: This environment variable holds the connection string required to connect your application to Azure Cosmos DB using the MongoDB API. It includes information like the database account endpoint, primary key, and other parameters.
- **Usage**: Set this variable in your Azure Web App Service configuration to enable your application to connect to the MongoDB API endpoint provided by Azure Cosmos DB.

### `AZURE_STORAGE_CONNECTION_STRING`
- **Purpose**: This variable contains the connection string to access Azure Storage services, such as Blob, Table, Queue, and File Storage. It includes the storage account name and key.
- **Usage**: Your application will use this connection string to perform operations like uploading or downloading files to/from Azure Blob Storage.

### `MONGODB_DATABASE_NAME`
- **Purpose**: This environment variable specifies the name of the MongoDB database that your application will interact with. This can be a database hosted on Azure Cosmos DB (using the MongoDB API) or a standalone MongoDB server.
- **Usage**: Configure this variable to tell your application which database to use when performing data operations.

### `AZURE_STORAGE_CONTAINER_NAME`
- **Purpose**: This variable specifies the name of the container within Azure Blob Storage where your application will store or retrieve blobs (files, images, etc.).
- **Usage**: Set this variable to ensure your application knows which container in Azure Storage to interact with.

## Setting Environment Variables in Azure Web App Service

1. **Navigate to Your Web App Service:**
    - Log in to the [Azure Portal](https://portal.azure.com).
    - Select your Web App Service instance.

2. **Access Configuration Settings:**
    - In the left-hand menu, select **Configuration** under **Settings**.

3. **Add Environment Variables:**
    - In the **Application settings** tab, click **+ New application setting**.
    - Enter the **Name** and **Value** for each environment variable listed above.
    - Click **OK** to save each setting.

4. **Save and Restart:**
    - After adding all the necessary environment variables, click **Save**.
    - Restart your Web App Service to apply the changes.

For more information on configuring environment variables, refer to the [Azure App Service Documentation](https://docs.microsoft.com/azure/app-service/configure-common).

