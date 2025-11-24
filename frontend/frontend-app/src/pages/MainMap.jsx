import { useEffect, useState } from "react";
import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
  useMapEvents,
} from "react-leaflet";
import "leaflet/dist/leaflet.css";
import "./../styles/common.css";

function LocationSelector({ onSelect }) {
  useMapEvents({
    click(e) {
      onSelect(e.latlng); // pass selected coordinates
    },
  });
  return null;
}

export default function MainMap() {
  const [incidents, setIncidents] = useState([]);
  const [filters, setFilters] = useState({ type: "", time: "all" });
  const [selectedLocation, setSelectedLocation] = useState(null);
  const token = localStorage.getItem("token"); // check if logged in

  useEffect(() => {
    fetch("http://localhost:8085/api/incidents")
      .then((res) => res.json())
      .then((data) => setIncidents(data));
  }, [filters]);

  const handleReport = async (e) => {
    e.preventDefault();
    if (!token) return alert("You must be logged in to report.");
    const formData = new FormData(e.target);
    formData.append("latitude", selectedLocation.lat);
    formData.append("longitude", selectedLocation.lng);

    await fetch("http://localhost:8085/api/incidents", {
      method: "POST",
      headers: { Authorization: `Bearer ${token}` },
      body: formData,
    });
  };

  return (
    <div className="auth-container">
      <div className="auth-card" style={{ width: "95%", maxWidth: "1000px" }}>
        <h2>Incident Map</h2>

        {/* Filters visible to all users */}
        <div style={{ marginBottom: "1rem" }}>
          <select
            onChange={(e) => setFilters({ ...filters, type: e.target.value })}
          >
            <option value="">All Types</option>
            <option value="Traffic">Traffic</option>
            <option value="Fire">Fire</option>
            <option value="Other">Other</option>
          </select>
          <select
            onChange={(e) => setFilters({ ...filters, time: e.target.value })}
          >
            <option value="all">All</option>
            <option value="24h">Last 24h</option>
            <option value="7d">Last 7 days</option>
            <option value="31d">Last 31 days</option>
          </select>
        </div>

        {/* Map */}
        <MapContainer
          center={[44.78, 17.19]}
          zoom={13}
          style={{ height: "500px", width: "100%" }}
        >
          <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
          {incidents.map((incident) => (
            <Marker
              key={incident.id}
              position={[incident.latitude, incident.longitude]}
            >
              <Popup>
                <strong>{incident.type}</strong>
                <br />
                {incident.description}
              </Popup>
            </Marker>
          ))}
          {token && <LocationSelector onSelect={setSelectedLocation} />}
        </MapContainer>

        {/* Report form only if logged in */}
        {token && selectedLocation && (
          <form className="auth-form" onSubmit={handleReport}>
            <select name="type">
              <option value="Traffic">Traffic</option>
              <option value="Fire">Fire</option>
              <option value="Other">Other</option>
            </select>
            <input name="subtype" placeholder="Subtype (optional)" />
            <textarea name="description" placeholder="Description" />
            <input type="file" name="image" />
            <button type="submit">Submit Incident</button>
          </form>
        )}
      </div>
    </div>
  );
}
