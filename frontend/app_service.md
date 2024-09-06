# Deploy ReactJS Application to Azure Web App Service

## Prerequisites

1. **Azure Account**: Ensure you have an Azure account. If not, create one at [Azure Portal](https://portal.azure.com).
2. **Azure CLI**: Install the Azure CLI. [Installation Instructions](https://docs.microsoft.com/cli/azure/install-azure-cli).
3. **Node.js and npm**: Ensure you have Node.js and npm installed on your local machine. [Download Node.js](https://nodejs.org/).

## Steps to Deploy a ReactJS Application

### 1. Prepare Your ReactJS Application

1. **Build Your ReactJS Application:**
    - Navigate to your ReactJS project directory.
    - Run the build command to create an optimized production build:
      ```bash
      npm run build
      ```

2. **Verify the Build:**
    - Ensure the `build` directory is created and contains the production-ready files.

### 2. Create an Azure Web App Service

1. **Log in to Azure:**
    - Open your terminal or command prompt and log in to Azure:
      ```bash
      az login
      ```

2. **Create a Resource Group:**
    - If you don’t already have a resource group, create one:
      ```bash
      az group create --name <your-resource-group> --location <your-region>
      ```
    - Replace `<your-resource-group>` with your preferred resource group name and `<your-region>` with your Azure region (e.g., `eastus`).

3. **Create an App Service Plan:**
    - Create an App Service Plan where your web app will be hosted:
      ```bash
      az appservice plan create --name <your-app-service-plan> --resource-group <your-resource-group> --sku B1
      ```
    - Replace `<your-app-service-plan>` with your preferred plan name.

4. **Create a Web App:**
    - Create a Web App instance within the App Service Plan:
      ```bash
      az webapp create --name <your-web-app-name> --resource-group <your-resource-group> --plan <your-app-service-plan> --runtime "NODE|18-lts"
      ```
    - Replace `<your-web-app-name>` with a unique name for your web app.

### 3. Deploy Your ReactJS Application

1. **Deploy Using Azure CLI:**
    - Use the Azure CLI to deploy your ReactJS application:
      ```bash
      az webapp deployment source config-zip --resource-group <your-resource-group> --name <your-web-app-name> --src <path-to-your-build-zip-file>
      ```
    - Replace `<path-to-your-build-zip-file>` with the path to the zip file of your `build` directory. You can create the zip file with:
      ```bash
      cd build
      zip -r ../build.zip .
      ```

### 4. Verify Your Deployment

1. **Access Your Web App:**
    - Open your web browser and navigate to `https://<your-web-app-name>.azurewebsites.net`.
    - You should see your ReactJS application running.

2. **Monitor and Manage Your Web App:**
    - Use the [Azure Portal](https://portal.azure.com) to monitor and manage your Web App Service.

## Additional Tips

- **Environment Variables**: Set environment variables in the Azure Portal under **Configuration** if your application requires them.
- **Scaling**: Configure scaling options in the App Service Plan to handle increased traffic.
- **Custom Domains**: Set up custom domains and SSL certificates for a branded experience.

For more detailed information, refer to the [Azure App Service Documentation](https://docs.microsoft.com/azure/app-service/).

