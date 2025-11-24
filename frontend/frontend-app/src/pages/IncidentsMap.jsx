import { useEffect, useState } from "react";
import MapView from "../components/MapView";
import "./../styles/common.css";

export default function IncidentsMap() {
  const [incidents, setIncidents] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8085/api/incidents")
      .then((res) => res.json())
      .then((data) => setIncidents(data));
  }, []);

  return (
    <div className="auth-container">
      <div className="auth-card" style={{ width: "90%", maxWidth: "900px" }}>
        <h2>Incident Map</h2>
        <MapView incidents={incidents} />
      </div>
    </div>
  );
}
