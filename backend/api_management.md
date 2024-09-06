# Azure API Management Service: Add POST and GET Operations

## Prerequisites

1. Ensure you have an Azure account and the Azure API Management service created.
2. Access to the Azure portal.

## Steps to Add a GET Operation for `/api/v1/customers`

1. **Navigate to Azure API Management Service:**
    - Log in to the [Azure Portal](https://portal.azure.com).
    - In the left-hand menu, select **All services**.
    - Search for and select **API Management services**.
    - Choose your API Management service instance.

2. **Select Your API:**
    - In the API Management service menu, select **APIs** under the **API Management** section.
    - Click on the API where you want to add the GET operation, or create a new API if needed.

3. **Add the GET Operation for `/api/v1/customers`:**
    - In the API menu, select **Design**.
    - Click on **+ Add Operation**.
    - In the **Add Operation** pane, fill in the details:
        - **Display Name**: `Get Customers`
        - **Name**: `get-customers`
        - **Method**: Select **GET** from the dropdown list.
        - **URL Template**: Enter `/api/v1/customers`.
    - Click **Save** to create the operation.

4. **Define the GET Operation:**
    - Configure request and response parameters, policies, and set up the backend service as needed.
    - Click **Save** after making your configurations.

5. **Test the GET Operation:**
    - In the **Design** tab, click on **Test**.
    - Ensure the **api-key** header is included in the request.
    - Click **Send** to test the GET operation.
   
## Steps to Add a POST Operation for `/api/v1/products`

1. **Navigate to Azure API Management Service:**
    - Follow the same steps as described in the GET operation section to navigate to your API.

2. **Select Your API:**
    - Choose the API to which you want to add the GET operation.

3. **Add the GET Operation for `/api/v1/products`:**
    - In the API menu, select **Design**.
    - Click on **+ Add Operation**.
    - In the **Add Operation** pane, fill in the details:
        - **Display Name**: `Create Product`
        - **Name**: `create-product`
        - **Method**: Select **POST** from the dropdown list.
        - **URL Template**: Enter `/api/v1/products`.
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

