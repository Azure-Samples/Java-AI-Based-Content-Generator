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
        az aks create \
        --resource-group <RESOURCE_GROUP_NAME> \
        --name <AKS_NAME> \
        --node-count 1 \
        --enable-addons monitoring \
        --enable-oidc-issuer \
        --enable-workload-identity \
        --generate-ssh-keys
        ```
   2. **Enable Managed Identity in AKS:**
   * Follow the Azure documentation to enable Managed Identity for the AKS cluster using workload identity: [Workload Identity for AKS](https://learn.microsoft.com/en-us/azure/aks/workload-identity-deploy-cluster).
   * Run the following commands to integrate workload identity:
   ```bash
   # Retrieve the OIDC issuer URL
    export AKS_OIDC_ISSUER="$(az aks show --name <AKS_NAME> \
    --resource-group <RESOURCE_GROUP_NAME> \            
    --query "oidcIssuerProfile.issuerUrl" \
    --output tsv)"
    
    # Create a Managed Identity for the AKS cluster
    az identity create \
    --name <USER_ASSIGNED_IDENTITY_NAME> \
    --resource-group <RESOURCE_GROUP_NAME> \
    --location <LOCATION> \
    --subscription "$(az account show --query id --output tsv)"
    export USER_ASSIGNED_CLIENT_ID="$(az identity show \
    --resource-group <RESOURCE_GROUP_NAME> \            
    --name <USER_ASSIGNED_IDENTITY_NAME> \                 
    --query 'clientId' \
    --output tsv)"
    
    # Create a Kubernetes service account
    cat <<EOF | kubectl apply -f -                                           
    apiVersion: v1
    kind: ServiceAccount
    metadata:
      annotations:azure.workload.identity/client-id: "${USER_ASSIGNED_CLIENT_ID}"
      name: workload-identity-sa
      namespace: default                       
    EOF
    
    # Create the federated identity credential
    az identity federated-credential create \
    --name ${FEDERATED_IDENTITY_CREDENTIAL_NAME} \
    --identity-name "${USER_ASSIGNED_IDENTITY_NAME}" \
    --resource-group "${RESOURCE_GROUP}" \
    --issuer "${AKS_OIDC_ISSUER}" \
    --subject system:serviceaccount:"${SERVICE_ACCOUNT_NAMESPACE}":"${SERVICE_ACCOUNT_NAME}" \
    --audience api://AzureADTokenExchange
    
    # Deploy your application
    cat <<EOF | kubectl apply -f -
    apiVersion: v1
    kind: Pod
    metadata:
      name: sample-workload-identity
      namespace: ${SERVICE_ACCOUNT_NAMESPACE}
      labels:azure.workload.identity/use: "true"  # Required. Only pods with this label can use workload identity.
    spec:
      serviceAccountName: ${SERVICE_ACCOUNT_NAME}
      containers:
        - image: <image>
          name: <containerName>
    EOF

   ```
3. **Grant Access to Key Vault:**
* Use the following command to grant the Managed Identity access to the Key Vault:
```bash
export KEYVAULT_RESOURCE_ID=$(az keyvault show --resource-group <RESOURCE_GROUP_NAME> \                     
    --name <KAYVAULT_NMAE> \          
    --query id \
    --output tsv)

export IDENTITY_PRINCIPAL_ID=$(az identity show \
    --name <USER_ASSIGNED_IDENTITY_NAME> \                 
    --resource-group <RESOURCE_GROUP_NAME> \            
    --query principalId \
    --output tsv)

az role assignment create \
    --assignee-object-id "${IDENTITY_PRINCIPAL_ID}" \
    --role "Key Vault Secrets User" \
    --scope "${KEYVAULT_RESOURCE_ID}" \
    --assignee-principal-type ServicePrincipal

export AZURE_KEYVAULT_URI=https://<KEYVAULT_NAME>.vault.azure.net/

```
4. **Creating Kubernetes Pod with Managed Identity Integration:**
   In this step, we will create a Kubernetes pod and integrate it with Key Vault using the Managed Identity.
    1. Deploy a Kubernetes Pod reference:
    * To define the pod with the necessary labels and annotations for Managed Identity integration.
   ```bash
   cat <<EOF | kubectl apply -f -
    apiVersion: v1
    kind: Pod
    metadata:
      name: workload-identity-key-vault
      namespace: default
    labels:azure.workload.identity/use: "true"
    spec:
      serviceAccountName: <SERVICE_ACCOUNT_NAME>   
      containers:
        - image: ghcr.io/azure/azure-workload-identity/msal-go
        name: oidc
        env:
          - name: AZURE_KEYVAULT_URI
            value: ${AZURE_KEYVAULT_URI}
      nodeSelector:kubernetes.io/os: linux
    EOF
     ```

Once these steps are completed, your AKS cluster will be able to access Key Vault secrets using Managed Identity.

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