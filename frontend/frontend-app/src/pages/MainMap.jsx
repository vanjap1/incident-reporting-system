import { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import { getIncidents } from "../services/api";
import MarkerClusterGroup from "react-leaflet-cluster";

export default function MainMap() {
  const [incidents, setIncidents] = useState([]);
  const position = [44.7722, 17.191]; // Default center (Banja Luka)

  useEffect(() => {
    const fetchIncidents = async () => {
      try {
        const response = await getIncidents();
        setIncidents(response.data);
      } catch (error) {
        console.error("Error fetching incidents:", error);
      }
    };
    fetchIncidents();
  }, []);

  return (
    <div style={{ height: "100vh", width: "100%" }}>
      <MapContainer
        center={position}
        zoom={13}
        style={{ height: "100%", width: "100%" }}
      >
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a>'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        <MarkerClusterGroup chunkedLoading>
          {incidents.map((incident) => (
            <Marker
              key={incident.id}
              position={[incident.latitude, incident.longitude]}
            >
              <Popup>
                <strong>{incident.type?.name}</strong> <br />
                {incident.subtype?.name} <br />
                {incident.description} <br />
                Status: {incident.status} <br />
                {incident.imageUrl && (
                  <img
                    src={incident.imageUrl}
                    alt="Incident"
                    style={{ width: "150px", marginTop: "5px" }}
                  />
                )}
              </Popup>
            </Marker>
          ))}
        </MarkerClusterGroup>
      </MapContainer>
    </div>
  );
}
