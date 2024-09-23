# Frontend Service

## Overview
The Frontend Service is a ReactJS-based application that allows users to submit queries, receive AI-generated content, and view similar products. The user must log in before interacting with the chat interface.

## Prerequisites
- Node.js
- React

## Environment Setup
1. **Clone the repository:**
   ```bash
   git clone https://github.com/Azure-Samples/Java-AI-Based-Content-Generator
   cd Java-AI-Based-Content-Generator/frontend
   ```
2. **Create the `.env` file:**
   * In the root directory of the project, there is a `.env.example` file that contains placeholder values for environment variables.
   * Copy this file to create your own `.env` file:
   ```bash
   cp .env.example .env
   ```
3. **Replace Placeholder Values:**
   * Open the newly created `.env` file and replace the placeholder values with the correct values for your local setup.
   * For example:
   ```properties
   REACT_APP_SERVICE_BASE_URL=http://localhost:8081
   REACT_APP_CLIENT_ID=<your-azure-app-client-id>
   REACT_APP_CONTENT_GENERATOR_ENDPOINT=/api/v1/generate/content
   REACT_APP_SERVICE_ACCESS_KEY=<service-access-key>  # Not needed for local setup
   ```
4. **Install dependencies**:
    ```bash
    npm install
    ```
5. **Run the application**:
   * After setting up the environment variables, you can start the development server using:
    ```bash
    npm start
    ```

## Deployment
- For Azure App Service deployment, see [app_service.md](./app_service.md).
- For AKS deployment, see [aks.md](./aks.md).
