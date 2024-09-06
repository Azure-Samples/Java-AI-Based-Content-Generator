# Environment Variables Configuration

This document provides an overview of the environment variables used in the application. These variables are crucial for the integration with external services and the backend service configuration.

## Environment Variables

| Variable Name                     | Description                                                                 | Example Value                                                                                                                          |
|-----------------------------------|-----------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| `AZURE_OPENAI_ENDPOINT_URL`        | The endpoint URL for accessing the Azure OpenAI service.                    | `https://<your-openai-instance>.openai.azure.com/openai/deployments/<deployment-name>/chat/completions?api-version=2023-03-15-preview` |
| `AZURE_OPENAI_KEY`                 | The API key for authenticating requests to the Azure OpenAI service.        | `your-azure-openai-api-key`                                                                                                            |
| `BACKEND_SERVICE_BASE_URL`         | The base URL for the backend service that the application communicates with.| `https://backend-service.example.com`                                                                                                  |
| `BACKEND_SERVICE_PRODUCT_ENDPOINT` | The specific endpoint for product-related operations in the backend service.| `/api/v1/products`                                                                                                                     |
| `BACKEND_SERVICE_ACCESS_KEY`       | The access key required for secure communication with the backend service.  | `your-backend-service-access-key`                                                                                                      |

## Variable Descriptions

- **`AZURE_OPENAI_ENDPOINT_URL`**:
    - This variable stores the endpoint URL for the Azure OpenAI service, which your application uses to send API requests.
    - The `<deployment-name>` in the URL should be replaced with the specific name of the deployment you created in the Azure AI services. This allows your application to interact with the deployed model.
    - Example: `https://<your-openai-instance>.openai.azure.com/openai/deployments/gpt-4o/chat/completions?api-version=2023-03-15-preview`.

- **`AZURE_OPENAI_KEY`**:
    - This variable holds the API key required for authenticating and authorizing your application’s requests to the Azure OpenAI service.
    - Keep this key secure and do not expose it in your codebase.

- **`BACKEND_SERVICE_BASE_URL`**:
    - This is the base URL of your backend service. All requests to your backend APIs will be prefixed with this URL.
    - This should include the protocol (`http` or `https`) and the domain name.

- **`BACKEND_SERVICE_PRODUCT_ENDPOINT`**:
    - This variable specifies the endpoint for handling product-related API requests within your backend service.
    - This path is appended to the `BACKEND_SERVICE_BASE_URL` to form the full URL for product API requests.

- **`BACKEND_SERVICE_ACCESS_KEY`**:
    - This is a secret key used to authenticate requests from your application to the backend service.
    - Like the API key, this should be kept secure and out of your codebase.
