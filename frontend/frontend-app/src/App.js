import AppRoutes from "./routes/AppRoutes";
import ErrorBoundary from "./errors/ErrorBoundary";
import { AuthProvider } from "./context/AuthContext";

function App() {
  return (
    <ErrorBoundary>
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>
    </ErrorBoundary>
  );
}

export default App;
