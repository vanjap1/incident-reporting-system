import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

// Function to set token dynamically
export const setAuthToken = (token) => {
  if (token) {
    api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  } else {
    delete api.defaults.headers.common["Authorization"];
  }
};

export const login = (credentials) => api.post("/auth/login", credentials);
export const register = (data) => api.post("/auth/register", data);
export const getIncidents = () => api.get("/incidents");
export const getIncidentTypes = () => api.get("/incident-types");
export const getIncidentSubtypes = () => api.get("/incident-subtypes");

export default api;
