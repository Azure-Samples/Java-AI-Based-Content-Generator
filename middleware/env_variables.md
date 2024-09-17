# Environment Variables

This document provides definitions for the environment variables used in the project. Ensure these variables are set appropriately for your development, testing, or production environments.

## Azure Key Vault

- **`AZURE_KEYVAULT_URI`**: The URI of the Azure Key Vault instance. This is used to access secrets stored in Azure Key Vault.
  - **Example**: `https://your-key-vault-name.vault.azure.net/`

## Azure Managed Identity

When using Azure Managed Identity, you may also need to configure the following environment variables for authentication:

- **`AZURE_CLIENT_ID`**: The client ID of the Azure Managed Identity used to authenticate against Azure services.
  - **Default**: Not required if using Azure Managed Identity directly.
  - **Example**: `your-client-id`

- **`AZURE_CLIENT_SECRET`**: The client secret for the Azure Managed Identity. This is used along with the client ID to authenticate.
  - **Default**: Not required if using Azure Managed Identity directly.
  - **Example**: `your-client-secret`

- **`AZURE_TENANT_ID`**: The tenant ID associated with your Azure subscription. This is needed for authentication and authorization.
  - **Default**: Not required if using Azure Managed Identity directly.
  - **Example**: `your-tenant-id`

## Default Values

When using Azure Managed Identity, you do not need to set `AZURE_CLIENT_ID`, `AZURE_CLIENT_SECRET`, or `AZURE_TENANT_ID` as these are managed by Azure and the system will use the managed identity's credentials.

For local development or if not using managed identities, ensure to set these environment variables appropriately.

