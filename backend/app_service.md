# Deploy Java Spring Boot Application (WAR) to Azure Web App Service

## Prerequisites

1. Ensure you have an Azure account.
2. Install [Azure CLI](https://docs.microsoft.com/cli/azure/install-azure-cli) if not already installed.
3. Install Java JDK 21 and [Maven](https://maven.apache.org/install.html).
4. Have a WAR file ready for deployment, named `backend.war`.

## Steps to Deploy

### 1. **Log in to Azure**

Open a terminal and log in to your Azure account:
```bash
az login
```

### 2. Create an Azure Resource Group (if needed)
Create a resource group to organize your resources:
```bash
az group create --name <your-resource-group> --location <your-location>
```
Replace `<your-resource-group>` with your desired resource group name and `<your-location>` with the Azure region, e.g., `eastus`.

### 3. Create an Azure App Service Plan
Create an App Service Plan to define the region and pricing tier for your web app:

```bash
az appservice plan create --name <your-app-service-plan> --resource-group <your-resource-group> --sku B1 --is-linux
```
Replace `<your-app-service-plan>` with your desired plan name.

### 4. Create a Web App
Create a Web App instance:

```bash
az webapp create --resource-group <your-resource-group> --plan <your-app-service-plan> --name <your-webapp-name> --runtime "JAVA|21-java21" --deployment-local-git
```
Replace `<your-webapp-name>` with your desired web app name. Make sure it’s unique within Azure.

### 5. Configure Deployment
Configure deployment credentials if needed:

```bash
az webapp deployment user set --user-name <username> --password <password>
```
Replace `<username>` and `<password>` with your preferred credentials.

### 6. Deploy the WAR File
#### Build the Application
Once you have added the required configurations in your `application.properties`, you can run the application using the following command:

```bash
./mvnw clean package
```

#### Using Azure CLI
Use the Azure CLI to deploy your WAR file:

```bash
az webapp deployment source config-zip --resource-group <your-resource-group> --name <your-webapp-name> --src ./target/backend.war
```

### 7. Verify the Deployment
Open your web app in a browser:

```bash
az webapp browse --resource-group <your-resource-group> --name <your-webapp-name>
```

Alternatively, navigate to https://<your-webapp-name>.azurewebsites.net to see your application running.

Reference:
* https://learn.microsoft.com/en-us/azure/app-service/quickstart-dotnetcore?tabs=net80&pivots=development-environment-azure-portal

