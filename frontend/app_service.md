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

_Reference: [az webapp deploy
](https://learn.microsoft.com/en-us/cli/azure/webapp?view=azure-cli-latest#az-webapp-deploy)_

1. **Deploy Using Azure CLI:**
    - Use the Azure CLI to deploy your ReactJS application (ZIP Method):
      ```bash
      zip -r build.zip ./build
      az webapp deploy --resource-group <your-resource-group> --name <your-web-app-name> --src-path build.zip --type zip
      ```
2. **Set the Correct Startup Command for React**

   Azure App Service needs to know how to serve your React app correctly. For static sites like React apps, you should configure the startup command to serve the static files and handle routing for single-page applications (SPA).
   
   Set the correct startup command:
   ```bash
   az webapp config set --resource-group <your-resource-group> --name <your-web-app-name> --startup-file "pm2 serve /home/site/wwwroot/build --no-daemon --spa"
   ```
3. **Clear Browser Cache**

   Sometimes the issue is on the client side. Clear your browser cache or use an incognito window to ensure that you're not loading an old version of the site.

4. **Ensure Build Files are Deployed in the Right Location**
   After deploying the build.zip, Azure App Service should have unzipped the files to the `/home/site/wwwroot/build` directory. Check the file structure on Azure by connecting to the App Service via the Kudu console or using FTP.

   * **Access the Kudu console:** Go to `https://<your-app-name>.scm.azurewebsites.net/DebugConsole`

   * **Verify the Files:** Navigate to the `/home/site/wwwroot/build` folder and verify if your React app's index.html and other assets are present there.
   
   * Set Environment Variables - [Reference](env_variables.md)
     ```bash
     az webapp config appsettings set -g <your-resource-group> -n <your-web-app-name> --settings <env_variable_key>=<env_variable_value>
     ```
5. **Restart the Web App**

   Sometimes the web app doesn’t reflect the changes immediately. You can restart the web app to make sure everything is refreshed:

   ```bash
   az webapp restart --name <your-web-app-name> --resource-group <your-resource-group>
   ```

### 4. Verify Your Deployment

1. **Access Your Web App:**
    - Open your web browser and navigate to `https://<your-web-app-name>.azurewebsites.net`.
    - You should see your ReactJS application running.

2. **Monitor and Manage Your Web App:**
    - Use the [Azure Portal](https://portal.azure.com) to monitor and manage your Web App Service.

3. **CLI**

   Open your web app in a browser:

   ```bash
   az webapp browse --resource-group <your-resource-group> --name <your-webapp-name>
   ```

### 5. Destroy the Azure Resources
To clean up and delete all the resources created (App Service, App Service Plan, and Resource Group), you can run the following command:

```bash
az group delete --name <your-resource-group> --yes --no-wait
```
This will delete the resource group and all associated resources.


## Additional Tips

- **Environment Variables** [Reference](env_variables.md) : Set environment variables in the Azure Portal under **Configuration** if your application requires them.
- **Scaling**: Configure scaling options in the App Service Plan to handle increased traffic.
- **Custom Domains**: Set up custom domains and SSL certificates for a branded experience.

For more detailed information, refer to the [Azure App Service Documentation](https://docs.microsoft.com/azure/app-service/).

