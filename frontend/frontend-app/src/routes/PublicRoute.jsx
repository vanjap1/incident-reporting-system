import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

export default function PublicRoute({ children }) {
  const { token } = useContext(AuthContext);

  if (token) {
    // If already authenticated, redirect to homepage
    return <Navigate to="/" replace />;
  }

  // Otherwise render the public component (Login/Register)
  return children;
}
