# Azure Key Vault Setup

## Assign Key Vault Roles

To allow your app to manage and access secrets in Azure Key Vault, follow these steps to assign roles via the Azure Portal.

### 1. Assign **Key Vault Administrator** Role

1. Go to the [Azure Portal](https://portal.azure.com).
2. In the left-hand menu, select **Resource groups** and choose the resource group that contains your Key Vault.
3. Select your **Key Vault** from the list of resources.
4. In the Key Vault menu, navigate to **Access control (IAM)**.
5. Click on the **+ Add** button at the top and select **Add role assignment**.
6. In the **Role** dropdown, search for and select **Key Vault Administrator**.
7. In the **Assign access to** field, choose **User, group, or service principal**.
8. Search for your user (or the user who will manage secrets) and click **Select**.
9. Finally, click **Review + assign**.

This role gives the user permission to manage secrets in the Key Vault.

### 2. Create **Key Vault Agent** Application
**Create App Registration:** 

For local development, you need to create an app registration in **Microsoft Entra ID** that will be used to access **Key Vault**.

**Steps:**

1. Go to **App Registration** in the Azure portal.
2. Select **App Registrations > New Registration**.
3. Enter a name for the app, select the supported account types, and click Register.
4. After registration, navigate to **Certificates & Secrets** and create a Client Secret.
5. Save the **Client ID**, **Client Secret**, and **Tenant ID**. You'll need these for local environment variables.

### 3. Assign **Key Vault Reader** Role for `Key Vault Agent` Application

To allow your app to read secrets from the Key Vault, follow these steps:

1. Follow the same steps as above to navigate to the **Access control (IAM)** menu of the Key Vault.
2. Click on **+ Add** and choose **Add role assignment**.
3. In the **Role** dropdown, search for and select **Key Vault Reader**.
4. In the **Assign access to** field, choose **Managed identity** or **App registration**, depending on where the identity is defined.
5. Search for your app's **Managed Identity** (or **Service Principal** in case of App Registration) and click **Select**.
6. Click **Review + assign** to finalize the assignment.

This ensures that the app can access the secrets in Key Vault.

## Add Secrets to Key Vault via Azure Portal

1. In the Azure Portal, navigate to your **Key Vault**.
2. In the left-hand menu, under **Settings**, select **Secrets**.
3. Click on **+ Generate/Import** at the top.
4. In the **Upload options**, select **Manual**.
5. Fill in the **Name** of the secret (e.g., `BackendServiceBaseUrl`) and the **Value** (e.g., `http://localhost:8080`).
6. Click **Create**.

Repeat this process to add all the required secrets, such as:

**For Backend Service:**
- `MiddlewareServiceBaseUrl`: `http://localhost:8081` (for local), or middleware APIM URL (for deployment)
- `MiddlewareServiceProductEmbeddingEndpoint`: `/api/v1/generate/embedding` (for local), or the middleware APIM endpoint
- `AzureCosmosConnectionString`
- `MongoDBDatabaseName`
- `AzureStorageConnectionString`
- `StorageContainerName`

**For Middleware Service:**
- `BackendServiceBaseUrl`: `http://localhost:8080` (for local), or backend APIM URL (for deployment)
- `BackendServiceProductEndpoint`: `/api/v1/product` (for local), or backend APIM endpoint
- `BackendServiceSimilarProductEndpoint`: `/api/v1/product/similar` (for local), or backend APIM endpoint
- `AzureOpenAiEndpointUrl` (OpenAI GPT-4o completion model **Target URI**)
- `AzureOpenAiAccessKey` (OpenAI GPT-4o **Key**)
- `AzureOpenAiEmbeddingEndpointUrl` (OpenAI text-embedding-3-small model **Target URI**)
- `AzureOpenAiEmbeddingKey` (OpenAI embedding model **Key**)


Once these secrets are added, they can be accessed by your application using the Managed Identity.

### Local Environment Setup
After setting up the Key Vault and App Registration, export the following environment variables in your local environment:
```bash
export AZURE_KEYVAULT_URL=<your_keyvault_url>
export AZURE_TENANT_ID=<your_tenant_id>
export AZURE_CLIENT_ID=<your_client_id>
export AZURE_CLIENT_SECRET=<your_client_secret>
