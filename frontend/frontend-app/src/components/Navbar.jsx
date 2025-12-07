import { NavLink } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import "../styles/navbar.css";

export default function Navbar() {
  const { token, setToken } = useContext(AuthContext);

  const handleLogout = () => {
    setToken(null);
    window.location.href = "/login";
  };

  return (
    <nav className="navbar navbar-expand-lg">
      <div className="container-fluid">
        <NavLink to="/" className="navbar-brand">
          <img src="/logo192.png" alt="Logo" height="40" />
        </NavLink>

        <div className="collapse navbar-collapse">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <NavLink to="/report" className="nav-link">
                Report Incident
              </NavLink>
            </li>
            {token && (
              <li className="nav-item">
                <NavLink to="/incidents" className="nav-link">
                  Incidents
                </NavLink>
              </li>
            )}
          </ul>

          <div className="d-flex">
            {!token ? (
              <>
                <NavLink to="/login" className="nav-link">
                  Login
                </NavLink>
                <NavLink to="/register" className="nav-link">
                  Register
                </NavLink>
              </>
            ) : (
              <button onClick={handleLogout} className="btn-logout">
                Logout
              </button>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}
