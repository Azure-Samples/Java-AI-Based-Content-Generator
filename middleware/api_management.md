# Azure API Management Service: Add POST and GET Operations

## Prerequisites

1. Ensure you have an Azure account and the Azure API Management service created.
2. Access to the Azure portal.

## Steps to Add a POST Operation for `/api/v1/generate/content`

1. **Navigate to Azure API Management Service:**
    - Follow the same steps as described in the GET operation section to navigate to your API.

2. **Select Your API:**
    - Choose the API to which you want to add the POST operation.

3. **Add the POST Operation for `/api/v1/generate/content`:**
    - In the API menu, select **Design**.
    - Click on **+ Add Operation**.
    - In the **Add Operation** pane, fill in the details:
        - **Display Name**: `Generate Content`
        - **Name**: `generate-content`
        - **Method**: Select **POST** from the dropdown list.
        - **URL Template**: Enter `/api/v1/generate/content`.
    - Click **Save** to create the operation.

4. **Define the POST Operation:**
    - Configure request and response parameters, policies, and set up the backend service as needed.
    - Click **Save** after making your configurations.

5. **Test the POST Operation:**
    - In the **Design** tab, click on **Test**.
    - Ensure the **api-key** header and **Content-Type** are included in the request.
    - Click **Send** to test the POST operation.
   

## Steps to Add a POST Operation for `/api/v1/generate/embeddings`

1. **Navigate to Azure API Management Service:**
    - Follow the same steps as described in the GET operation section to navigate to your API.

2. **Select Your API:**
    - Choose the API to which you want to add the POST operation.

3. **Add the POST Operation for `/api/v1/generate/embeddings`:**
    - In the API menu, select **Design**.
    - Click on **+ Add Operation**.
    - In the **Add Operation** pane, fill in the details:
        - **Display Name**: `Generate Embeddings`
        - **Name**: `generate-embeddings`
        - **Method**: Select **POST** from the dropdown list.
        - **URL Template**: Enter `/api/v1/generate/embeddings`.
    - Click **Save** to create the operation.

4. **Define the POST Operation:**
    - Configure request and response parameters, policies, and set up the backend service as needed.
    - Click **Save** after making your configurations.

5. **Test the POST Operation:**
    - In the **Design** tab, click on **Test**.
    - Ensure the **api-key** header and **Content-Type** are included in the request.
    - Click **Send** to test the POST operation.
   
## Additional Tips

- **Versioning**: Consider versioning your API if you expect multiple versions to be used concurrently.
- **Policies**: Use policies to transform requests and responses or to implement security measures.
- **Documentation**: Document your API operations to provide clarity for users.

For more detailed information, refer to the [Azure API Management documentation](https://docs.microsoft.com/azure/api-management/).

