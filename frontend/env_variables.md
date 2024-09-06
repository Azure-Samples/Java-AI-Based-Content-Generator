# Environment Variables for React.js Application

This document provides details about the environment variables required for the React.js application. These variables are essential for configuring the application to interact with various services and APIs.

## Environment Variables Table

| Variable Name                    | Description                                                              | Example Value                                           | How to Set / Obtain                                                           |
|---------------------------------|--------------------------------------------------------------------------|---------------------------------------------------------|-------------------------------------------------------------------------------|
| `REACT_APP_SERVICE_BASE_URL`     | The base URL for your application service. This is typically the URL of your backend service or API. | `https://<your-app-service-name>.azurewebsites.net`   | Replace `<your-app-service-name>` with your Azure App Service name.           |
| `REACT_APP_CLIENT_ID`            | The client ID for Azure App Registration used for MSAL (Microsoft Authentication Library) login. | `<azure-app-registration-client-id-for-msal-login>`  | Find this in the Azure portal under Azure Active Directory > App registrations. |
| `REACT_APP_CONTENT_GENERATOR_ENDPOINT` | The endpoint for the content generation API. | `/api/v1/generate/content`                             | This should be the path where your content generation service API is available. |
| `REACT_APP_SERVICE_ACCESS_KEY`   | The access key used for authenticating requests to your service. | `<service-access-key>`                                 | Service access key |

## Setting Environment Variables

In a development environment, you can set these variables in a `.env` file in the root of your React project:

```plaintext
REACT_APP_SERVICE_BASE_URL=https://<your-app-service-name>.azurewebsites.net
REACT_APP_CLIENT_ID=<azure-app-registration-client-id-for-msal-login>
REACT_APP_CONTENT_GENERATOR_ENDPOINT=/api/v1/generate/content
REACT_APP_SERVICE_ACCESS_KEY=<service-access-key>
