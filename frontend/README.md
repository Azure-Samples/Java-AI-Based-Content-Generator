# Marketing Content Generator Frontend

This is the frontend application for the Marketing Content Generator, built using React.js. It interacts with the backend API to generate and display marketing content.

## Features

- User interface for interacting with the marketing content generation API.
- Authentication using Azure AD.
- Display and manage generated content.

## Prerequisites

- Node.js (v14 or later)
- Yarn (recommended, but npm can also be used)
- Azure App Registration for authentication

## Getting Started

1. **Clone the Repository**

   ```bash
   git clone https://github.com/terawe/contentgenerator.git
   cd frontend
   ```
2. **Install Dependencies**

   Install the required npm packages using Yarn (or npm):
    ```bash
   yarn install
    # or
    npm install
    ```
3. **Setup Environment Variables**

   Create a `.env` file in the root directory of your project and include the following environment variables. Refer to the [env_variables.md](env_variables.md) for more details.
    ```properties
    REACT_APP_SERVICE_BASE_URL=https://<your-app-service-name>.azurewebsites.net
    REACT_APP_CLIENT_ID=<azure-app-registration-client-id-for-msal-login>
    REACT_APP_CONTENT_GENERATOR_ENDPOINT=/api/v1/generate/content
    REACT_APP_SERVICE_ACCESS_KEY=<service-access-key>
    ```
4. **Run the Application**

    Start the development server with:
    ```bash
    yarn start
    # or
    npm start
    ```
   This will start the React application in development mode and you can view it at `http://localhost:3000`.

5. **Build the Application**
   To create a production build of the application, use:
    ```bash
    yarn build
    # or
    npm run build
    ```
## Deployment Methods
### 1. Deploying to Azure App Service
You can deploy the React application to Azure App Service. Detailed instructions for deploying to Azure App Service can be found in the [App Service Deployment Guide](app_service.md).
* **Application URL**: After deployment, your application will be accessible via the provided URL from Azure App Service.

### 2. Deploying to Azure Kubernetes Service (AKS)

You can deploy the application to an Azure Kubernetes Service (AKS) cluster. Detailed instructions are provided in the [AKS Deployment Guide](aks.md).

### Authentication
The application uses Azure Active Directory (Azure AD) for user authentication. Ensure that the Azure AD app registration is configured correctly, and that the `REACT_APP_CLIENT_ID` is set up properly in your `.env` file.

### API Integration
The application integrates with the backend API to fetch and display content. Ensure that the `REACT_APP_SERVICE_BASE_URL` and `REACT_APP_CONTENT_GENERATOR_ENDPOINT` are correctly set to point to your backend API.

### Notes
* Ensure that the environment variables are correctly set for both development and production environments.

