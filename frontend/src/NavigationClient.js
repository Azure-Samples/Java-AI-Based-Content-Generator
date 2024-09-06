import { NavigationClient } from "@azure/msal-browser";

/**
 * This is an example for overriding the default function MSAL uses to navigate to other URLs in your webpage.
 */
export class CustomNavigationClient extends NavigationClient {
    constructor(navigate) {
        super();
        this.navigate = navigate; // Assigning the React Router navigate function
    }

    /**
     * Navigates to other pages within the same web application.
     * You can use the useNavigate hook provided by react-router-dom to take advantage of client-side routing.
     * @param url
     * @param options
     */
    async navigateInternal(url, options) {
        const relativePath = url.replace(window.location.origin, "");

        try {
            if (options.noHistory) {
                this.navigate(relativePath, { replace: true });
            } else {
                this.navigate(relativePath);
            }
        } catch (error) {
            console.error("Navigation error:", error);
        }

        return false; // Indicate that the navigation was handled
    }
}
