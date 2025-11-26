// Navbar.jsx
import React from "react";
import { Link } from "react-router-dom";

export default function Navbar({ isAuthenticated, roles, onLogout }) {
  return (
    <nav
      style={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "10px",
        backgroundColor: "#333",
        color: "#fff",
      }}
    >
      {/* Left side: app title */}
      <div>
        <Link to="/" style={{ color: "#fff", textDecoration: "none" }}>
          Incident Map
        </Link>
      </div>

      {/* Right side: auth buttons */}
      <div>
        {!isAuthenticated ? (
          <>
            <Link
              to="/login"
              style={{
                marginRight: "10px",
                color: "#fff",
                textDecoration: "none",
              }}
            >
              Login
            </Link>
            <Link
              to="/register"
              style={{ color: "#fff", textDecoration: "none" }}
            >
              Register
            </Link>
          </>
        ) : (
          <>
            {/* Common for all logged-in users */}
            <button
              onClick={onLogout}
              style={{
                marginRight: "10px",
                background: "transparent",
                border: "1px solid #fff",
                color: "#fff",
                cursor: "pointer",
              }}
            >
              Logout
            </button>

            {/* Role-specific options */}
            {roles?.includes("USER") && (
              <Link
                to="/report"
                style={{
                  marginRight: "10px",
                  color: "#fff",
                  textDecoration: "none",
                }}
              >
                Report Incident
              </Link>
            )}
            {roles?.includes("MODERATOR") && (
              <Link
                to="/moderator"
                style={{ color: "#fff", textDecoration: "none" }}
              >
                Moderator Dashboard
              </Link>
            )}
          </>
        )}
      </div>
    </nav>
  );
}
