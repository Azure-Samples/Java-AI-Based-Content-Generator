import "./App.scss";
import { Route, Routes, useNavigate } from "react-router-dom";
import { Header } from "./components/common/Header";
import { Landing } from "./components/Landing";
import {
  AuthenticatedTemplate,
  MsalAuthenticationTemplate,
  MsalProvider,
  useMsal
} from "@azure/msal-react";
import { useEffect, useState } from "react";
import { callMsGraph } from "./graph";
import {
  InteractionRequiredAuthError,
  InteractionStatus,
  InteractionType
} from "@azure/msal-browser";
import { loginRequest } from "./authConfig";
import { CustomNavigationClient } from "./NavigationClient";
import { ErrorComponent } from "./ui-components/ErrorComponent";
import { Loading } from "./ui-components/Loading";
import { Provider } from "react-redux";
import { store } from "./store/store";
import About from "./components/About";

function App({ pca }) {
  const navigate = useNavigate();
  const navigationClient = new CustomNavigationClient(navigate);
  pca.setNavigationClient(navigationClient);

  const { instance, inProgress } = useMsal();
  const [graphData, setGraphData] = useState(null);

  useEffect(() => {
    if (!graphData && inProgress === InteractionStatus.None) {
      callMsGraph()
          .then((response) => setGraphData(response))
          .catch((e) => {
            if (e instanceof InteractionRequiredAuthError) {
              instance.acquireTokenRedirect({
                ...loginRequest,
                account: instance.getActiveAccount()
              });
            } else {
              console.error("Error fetching graph data:", e);
            }
          });
    }
  }, [inProgress, graphData, instance]);

  const authRequest = {
    ...loginRequest
  };

  return (
      <Provider store={store}>
        <MsalProvider instance={pca}>
          <MsalAuthenticationTemplate
              interactionType={InteractionType.Redirect}
              authenticationRequest={authRequest}
              errorComponent={ErrorComponent}
              loadingComponent={Loading}
          >
            <AuthenticatedTemplate>
              <div className="App">
                <Header />
                <main>
                  <Routes>
                    <Route path="/" element={<Landing />} />
                    <Route path="/about" element={<About />} />
                  </Routes>
                </main>
              </div>
            </AuthenticatedTemplate>
          </MsalAuthenticationTemplate>
        </MsalProvider>
      </Provider>
  );
}

export default App;
