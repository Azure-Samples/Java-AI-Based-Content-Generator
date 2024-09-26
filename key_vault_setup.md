# Azure Key Vault Setup

## Assign Key Vault Roles

To manage and access secrets in Azure Key Vault, follow these steps to assign roles via the Azure Portal.

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

### 2. Configure User Identity for Local Development

For local development, you can use Azure CLI credentials to authenticate with Azure Key Vault. Follow these steps:


1. **Install Azure CLI:**

    * Ensure that the [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli) is installed on your local machine.
   
2. **Login to Azure CLI:**

    * Open your command-line interface (CLI) and run the following command to login:
      ```bash
      az login
      ```
    * This command will open a browser window prompting you to authenticate with your Azure credentials. If the browser does not open automatically, follow the instructions in the CLI to complete the login process.
    * After successful login, Azure CLI stores the necessary credentials locally, allowing your application to access Azure resources based on your authenticated session.

3. **Configure Spring Cloud Azure:** ([Spring Cloud Azure authentication](https://microsoft.github.io/spring-cloud-azure/current/reference/html/index.html#authentication))
    * Ensure that you have the [Spring Cloud Azure](https://spring.io/projects/spring-cloud-azure#overview) dependencies added to your project. Spring Cloud Azure provides integration with Azure services and supports using different credential types.
    * By default, Spring Cloud Azure will attempt to use multiple credential builders to authenticate, including:
      * `EnvironmentCredential`
      * `WorkloadIdentityCredential`
      * `ManagedIdentityCredential`
      * `SharedTokenCacheCredential`
      * `IntelliJCredential`
      * `VSCodeCredential`
      * `AzureCliCredential`
    * For local development, the application will use AzureCliCredential if no other credentials are configured. This ensures that your local environment can authenticate and interact with Azure services seamlessly.

4. **Verify the Configuration:**
    * To ensure that your CLI is correctly configured, you can run:
    ```bash
      az login
      ```
   * This command displays the details of the currently authenticated account. Ensure that it matches the expected account used for your Azure resources. 


### 3. Use Managed Identity for Azure App Service

For applications running on Azure App Service, follow these steps to configure Managed Identity:

1. **Enable Managed Identity for Your App Service:**
   * Go to the Azure Portal.
   * Navigate to your App Service and select **Identity** under the **Settings** section.
   * Turn on the **System assigned managed identity**.
   * Click **Save**.

2. **Enable Managed Identity for Your App Service:**
    * Go to your Key Vault in the Azure Portal.
    * Navigate to **Access policies**.
    * Click **+ Add Access Policy**.
    * In the **Configure from template** section, select **Secret Management** (Get, List).
    * Under Select principal, find and select your App Service's managed identity.
    * Click **Add**, then **Save**.

This will allow your App Service to access Key Vault secrets using Managed Identity.



### 4. Azure Key Vault with AKS


* #### 4.1 Using Managed Identity in Azure Kubernetes Service (AKS)

  Reference: [Use a managed identity in Azure Kubernetes Service (AKS)](https://learn.microsoft.com/en-us/azure/aks/use-managed-identity)


  **Azure Portal:**

   1. **Assign Managed Identity to AKS:**
      * Go to the Azure Portal.
      * Navigate to your AKS Cluster.
      * Under **Settings**, select **Managed Identity**.
      * Enable the system-assigned Managed Identity for your AKS cluster.

   2. **Grant Access to Key Vault:**
      * Go to your Azure Key Vault.
      * Under **Access policies**, create a new access policy.
      * Select the **Secret Management** template.
      * Under **Principal**, select the Managed Identity of your AKS cluster.
      * Save the changes to grant AKS access to the Key Vault secrets.

   3. **Configure Pod Identity on AKS:**
      * To access the Key Vault from within AKS pods, you'll need to configure AAD Pod Identity.
      * Go to the AKS Cluster page and look for **AAD Pod Identity** under **Settings**. Follow the instructions to install and configure the pod identity for your workload.

   4. **Access Key Vault Secrets from the Application:**
      * Once the Managed Identity is set up and access policies are configured, you can use the same Java Spring Boot code to retrieve secrets from Azure Key Vault. Refer to the earlier Java code in the Key Vault Secret Access section.

   5. **Configure Environment Variables in AKS:**
      * Go to your AKS cluster in the Azure Portal.
      * Navigate to **Workloads** and select the appropriate **Deployment** or **Pod**.
      * Set the `AZURE_KEYVAULT_URL` as an environment variable for the application, pointing to your Key Vault URL.

  **CLI:**

   1. **Enable Managed Identity for AKS:**
      * To enable Managed Identity when creating an AKS cluster, use the following command:
        ```bash
        az aks create --resource-group <resource-group-name> --name <aks-cluster-name> --enable-managed-identity
        ```
      * If AKS is already deployed, enable Managed Identity with:
        ```bash
        az aks update --resource-group <resource-group-name> --name <aks-cluster-name> --enable-managed-identity
        ```

   2. **Grant Access to Key Vault:**
      * Get AKS Managed Identity's client ID:
        ```bash
        IDENTITY_CLIENT_ID=$(az aks show --resource-group <resource-group-name> --name <aks-cluster-name> --query identityProfile.kubeletidentity.clientId --output tsv)
        ```
      * Grant AKS Managed Identity access to Key Vault:
        ```bash
        az keyvault set-policy --name <key-vault-name> --secret-permissions get list --object-id $IDENTITY_CLIENT_ID
        ```

   3. **AAD Pod Identity on AKS:**
      * You can mount the secrets directly into Kubernetes pods or use environment variables.




* #### 4.2 Azure Key Vault Secrets Store CSI Driver with Azure Identity Provider
  For applications running on AKS, follow these steps to configure Managed Identity:

   1. **Create and Assign a User-Assigned Managed Identity::**
      * Create a user-assigned managed identity in the Azure Portal or using Azure CLI.
      * Go to your AKS cluster in the Azure Portal.
      * Under **Settings**, select **Managed identity**.
      * Assign the user-assigned managed identity to your AKS cluster.

   2. **Configure Secrets Store CSI Driver:**
      * Install the Secrets Store CSI driver and Azure Key Vault provider in your AKS cluster.
      * Configure a SecretProviderClass to specify the user-assigned managed identity and Key Vault details.
      * Create a Kubernetes SecretProviderClass resource and configure your pods to use it.

  For more detailed steps, refer to Azure’s guide on using [Secrets Store CSI driver with AKS](https://learn.microsoft.com/en-us/azure/aks/csi-secrets-store-identity-access?tabs=azure-portal&pivots=access-with-a-user-assigned-managed-identity).





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

### Environment Setup
After setting up the Key Vault and App Registration, export the following environment variables in your local environment:
```bash
export AZURE_KEYVAULT_URL=<your_keyvault_url>
```