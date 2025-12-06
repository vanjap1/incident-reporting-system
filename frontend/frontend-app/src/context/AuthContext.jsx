import React, { createContext, useState, useEffect } from "react";
import { setAuthToken } from "../services/api";

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [token, setToken] = useState(localStorage.getItem("token"));

  useEffect(() => {
    if (token) {
      localStorage.setItem("token", token);
      setAuthToken(token); // sync with Axios
    } else {
      localStorage.removeItem("token");
      setAuthToken(null);
    }
  }, [token]);

  return (
    <AuthContext.Provider value={{ token, setToken }}>
      {children}
    </AuthContext.Provider>
  );
}
