import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import {EventType, PublicClientApplication} from "@azure/msal-browser";
import {msalConfig} from "./authConfig";
import {HashRouter} from "react-router-dom";

export const msalInstance = new PublicClientApplication(msalConfig);

msalInstance.initialize().then(() => {
    // Account selection logic is app dependent. Adjust as needed for different use cases.
    const accounts = msalInstance.getAllAccounts();
    if (accounts.length > 0) {
        msalInstance.setActiveAccount(accounts[0]);
    }

    msalInstance.addEventCallback((event) => {
        if (event.eventType === EventType.LOGIN_SUCCESS && event.payload) {
            const payload = event.payload;
            const account = payload.account;
            msalInstance.setActiveAccount(account);
        }
    });
    const root = ReactDOM.createRoot(
        document.getElementById("root")
);
    root.render(
        <HashRouter>
            <App pca={msalInstance} />
        </HashRouter>
    );
}).catch((error) => {
    console.error('MSAL initialization failed:', error);
    msalInstance.clearCache();
});