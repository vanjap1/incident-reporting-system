import React, { useContext } from "react";
import { NavLink } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

export default function Navbar() {
  const { token, setToken } = useContext(AuthContext);

  const handleLogout = () => {
    setToken(null);
    window.location.href = "/login";
  };

  return (
    <nav
      style={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "1rem",
        backgroundColor: "#f5f5f5",
      }}
    >
      <div>
        <NavLink
          to="/"
          style={({ isActive }) => ({
            marginRight: "1rem",
            fontWeight: isActive ? "bold" : "normal",
          })}
        >
          Home
        </NavLink>

        {token && (
          <NavLink
            to="/incidents"
            style={({ isActive }) => ({
              marginRight: "1rem",
              fontWeight: isActive ? "bold" : "normal",
            })}
          >
            Incidents
          </NavLink>
        )}
      </div>

      <div>
        {!token ? (
          <>
            <NavLink
              to="/login"
              style={({ isActive }) => ({
                marginRight: "1rem",
                fontWeight: isActive ? "bold" : "normal",
              })}
            >
              Login
            </NavLink>
            <NavLink
              to="/register"
              style={({ isActive }) => ({
                fontWeight: isActive ? "bold" : "normal",
              })}
            >
              Register
            </NavLink>
          </>
        ) : (
          <button onClick={handleLogout}>Logout</button>
        )}
      </div>
    </nav>
  );
}
