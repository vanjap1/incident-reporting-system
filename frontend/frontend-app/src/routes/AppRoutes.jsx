import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "../components/Navbar";
import Login from "../pages/Login";
import Register from "../pages/Register";
import MainMap from "../pages/MainMap";
import ReportIncident from "../pages/ReportIncident";
import UserIncidents from "../pages/UserIncidents";
import NotFound from "../errors/NotFound";
import ProtectedRoute from "./ProtectedRoute";
import PublicRoute from "./PublicRoute";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route
          path="/login"
          element={
            <PublicRoute>
              <Login />
            </PublicRoute>
          }
        />
        <Route
          path="/register"
          element={
            <PublicRoute>
              <Register />
            </PublicRoute>
          }
        />
        <Route
          path="/incidents"
          element={
            <ProtectedRoute>
              <UserIncidents />
            </ProtectedRoute>
          }
        />
        <Route path="/" element={<MainMap />} />
        <Route path="/report" element={<ReportIncident />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}
