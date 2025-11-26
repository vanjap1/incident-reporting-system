// MainMap.jsx
import React from "react";
import MapView from "../components/MapView";
import Navbar from "../components/Navbar";

export default function MainMap({
  incidents,
  isAuthenticated,
  role,
  onLogout,
}) {
  return (
    <div style={{ display: "flex", flexDirection: "column", height: "100vh" }}>
      {/* Top navigation bar */}
      <Navbar
        isAuthenticated={isAuthenticated}
        role={role}
        onLogout={onLogout}
      />

      {/* Map view fills the rest of the page */}
      <div style={{ flex: 1 }}>
        <MapView incidents={incidents} />
      </div>
    </div>
  );
}
