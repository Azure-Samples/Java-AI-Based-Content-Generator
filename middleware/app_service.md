# Deploy Java Spring Boot Application (WAR) to Azure Web App Service

## Prerequisites

1. Ensure you have an Azure account.
2. Install [Azure CLI](https://docs.microsoft.com/cli/azure/install-azure-cli) if not already installed.
3. Install Java JDK 17 and [Maven](https://maven.apache.org/install.html).
4. Have a WAR file ready for deployment, named `middleware.war`.


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
az webapp create --resource-group <your-resource-group> --plan <your-app-service-plan> --name <your-webapp-name> --runtime "JAVA|17-java17" --deployment-local-git
```
Replace `<your-webapp-name>` with your desired web app name. Make sure it’s unique within Azure.

### 5. Configure Deployment
Configure deployment credentials if needed:

```bash
az webapp deployment user set --user-name <username> --password <password>
```
Replace `<username>` and `<password>` with your preferred credentials.

### 6. Key Vault Secret Access - [Link](../backend/app_service.md#6-key-vault-secret-access)

### 7. Deploy the WAR File

#### 7.1 Using Maven Plugin
```bash
./mvnw clean package azure-webapp:deploy
```

This command will:
* Deploy the WAR file to your specified Azure Web App.
* Ensure that the Azure configuration in your `pom.xml` is used to target the correct Azure resources.

> ##### Configuration Requirements
Ensure that you have the **Azure Web App Maven Plugin** set up in your `pom.xml`. Below is an example configuration that you should include in the `build` section of your `pom.xml` if not already present:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.microsoft.azure</groupId>
            <artifactId>azure-webapp-maven-plugin</artifactId>
            <version>2.13.0</version>
            <!-- Refer: https://github.com/microsoft/azure-maven-plugins/wiki/Azure-Web-App:-Configuration-Details -->

            <configuration>
                <!-- Optional: If not mentioned, it will pick the default subscription -->
                <!--                    <subscriptionId>SUBSCRIPTION_ID</subscriptionId>-->

                <resourceGroup>BACKEND_RESOURCE_GROUP</resourceGroup>
                <appName>BACKEND_APP_SERVICE_NAME</appName>

                <!-- Required only when creating a new Azure App Service. Not needed for updates. -->
                <!--                    <region>REGION</region>-->
                <!--                    <pricingTier>B1</pricingTier>-->


                <runtime>
                    <os>Linux</os>
                    <javaVersion>Java 17</javaVersion>
                    <webContainer>Tomcat 10.0</webContainer>
                </runtime>
                <deployment>
                    <resources>
                        <resource>
                            <directory>${project.basedir}/target</directory>
                            <includes>
                                <include>*.war</include>
                            </includes>
                        </resource>
                    </resources>
                </deployment>
            </configuration>
        </plugin>
    </plugins>
</build>

```

Make sure that:

* `resourceGroup` matches the Azure Resource Group where your Web App is hosted.
* `appName` corresponds to the name of your Azure Web App.
* `region` matches the location of your Web App (e.g., `westus`, `eastus`).
* `javaVersion` and `webContainer` reflect your environment.


#### 7.2 Using Azure CLI

#### Build the Application - [Reference](env_variables.md)
Once you have added the required configurations in your `application.properties`, you can run the application using the following command:

```bash
./mvnw clean package
```

Use the Azure CLI to deploy your WAR file:

```bash
az webapp deploy --resource-group <your-resource-group> --name <your-webapp-name> --src-path ./target/backend.war --type war --async true
```

### 8. Verify the Deployment
Open your web app in a browser:

```bash
az webapp browse --resource-group <your-resource-group> --name <your-webapp-name>
```

Alternatively, navigate to https://<your-webapp-name>.azurewebsites.net to see your application running.

### 9. Destroy the Azure Resources
To clean up and delete all the resources created (App Service, App Service Plan, and Resource Group), you can run the following command:

```bash
az group delete --name <your-resource-group> --yes --no-wait
```
This will delete the resource group and all associated resources.


Reference:
* https://learn.microsoft.com/en-us/azure/app-service/quickstart-dotnetcore?tabs=net80&pivots=development-environment-azure-portal
