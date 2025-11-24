import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";

export default function MapView({ incidents }) {
  return (
    <MapContainer
      center={[44.78, 17.19]}
      zoom={13}
      style={{ height: "500px", width: "100%" }}
    >
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution="&copy; OpenStreetMap contributors"
      />
      {incidents.map((incident) => (
        <Marker
          key={incident.id}
          position={[incident.latitude, incident.longitude]}
        >
          <Popup>
            <strong>{incident.title}</strong>
            <br />
            {incident.description}
          </Popup>
        </Marker>
      ))}
    </MapContainer>
  );
}
