// Config object to be passed to Msal on creation

import { createBrowserHistory } from "history";
import { CustomNavigationClient } from "./NavigationClient";

const history = createBrowserHistory();
export const msalConfig = {
  auth: {
    clientId: process.env.REACT_APP_CLIENT_ID,
    authority: "https://login.microsoftonline.com/common",
    redirectUri: "/",
    postLogoutRedirectUri: "/"
  },
  cache: {
    cacheLocation: "sessionStorage"
  },
  system: {
    allowNativeBroker: false, // Disables WAM Broker
    navigationClient: new CustomNavigationClient(history)
  }
};

// Add here scopes for id token to be used at MS Identity Platform endpoints.
export const loginRequest = {
  scopes: ["User.Read"],
  prompt: "select_account"
};

// Add here the endpoints for MS Graph API services you would like to use.
export const graphConfig = {
  graphMeEndpoint: "https://graph.microsoft.com/v1.0/me"
};
