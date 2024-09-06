## Azure API Management Service Provisioning and Configuration Guide

### 1. Provision Azure API Management Service

To provision an Azure API Management service, refer to [APIM Service Creation Guide](https://azure.github.io/apim-lab/apim-lab/1-apim-creation/apim-creation-1-1-instantiate.html).


### 2. Add API Operations

To add GET and POST operations refer to [Adding APIs from Scratch](https://azure.github.io/apim-lab/apim-lab/3-adding-apis/adding-apis-3-1-from-scratch.html).


### 3. Configure Inbound Policy to Check Header

To add an inbound policy that checks for the `api-key` header, follow these steps:

1. **Open Policy Editor**:
    * In your API, go to the **Design** tab and select **Inbound processing**.
   
2. **Add the Following Policy**:
    ```xml
   <inbound>
        <base />
        <check-header name="api-key" failed-check-httpcode="401" failed-check-error-message="API key missing or invalid" ignore-case="true">
            <value>YOUR_API_KEY</value>
        </check-header>
    </inbound>
    ```
   * Replace `YOUR_API_KEY` with the actual API key value.

3. **Save the Policy**:
   * Click **Save** to apply the policy.

### 4. (Optional) Add CORS Policy

To add a CORS policy, follow the instructions in the [Azure documentation](https://azure.github.io/apim-lab/apim-lab/4-policy-expressions/policy-expressions-4-1-cors.html):

1. **Open Policy Editor**:
    * In the **Design** tab of your API, select **Inbound processing**.
   
2. **Add the Following CORS Policy**:
   ```xml
   <inbound>
    <!-- Other Policies  -->
        <cors>
            <allowed-origins>
                <origin>*</origin> <!-- Adjust this as needed -->
            </allowed-origins>
            <allowed-methods>
                <method>GET</method>
                <method>POST</method>
                <!-- Add other methods if needed -->
            </allowed-methods>
            <allowed-headers>
                <header>*</header> <!-- Adjust this as needed -->
            </allowed-headers>
        </cors>
    </inbound>
    ```
3. **Save the Policy**:
   * Click Save to apply the policy.


