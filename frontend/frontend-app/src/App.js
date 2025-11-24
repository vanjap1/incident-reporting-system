import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ReportIncident from "./pages/ReportIncident";
import IncidentsMap from "./pages/IncidentsMap";
import MainMap from "./pages/MainMap";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/report" element={<ReportIncident />} />
        <Route path="/map" element={<IncidentsMap />} />
        <Route path="*" element={<MainMap />} />
      </Routes>
    </Router>
  );
}

export default App;
