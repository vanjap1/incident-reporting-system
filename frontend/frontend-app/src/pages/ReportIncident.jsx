import React, { useState, useEffect, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import api, { getIncidentTypes, getIncidentSubtypes } from "../services/api";
import "../styles/forms.css"; // import the shared form styles

export default function ReportIncident() {
  const { token } = useContext(AuthContext);

  const [formData, setFormData] = useState({
    latitude: "",
    longitude: "",
    typeId: "",
    subtypeId: "",
    description: "",
    imageUrl: "",
  });

  const [types, setTypes] = useState([]);
  const [subtypes, setSubtypes] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [typesRes, subtypesRes] = await Promise.all([
          getIncidentTypes(),
          getIncidentSubtypes(),
        ]);
        setTypes(typesRes.data);
        setSubtypes(subtypesRes.data);
      } catch (error) {
        console.error("Error fetching types/subtypes:", error);
      }
    };
    fetchData();
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post("/incidents", formData);
      alert("Incident reported successfully!");

      if (token) {
        await api.post("/incidents/notify", { incidentId: response.data.id });
      }

      setFormData({
        latitude: "",
        longitude: "",
        typeId: "",
        subtypeId: "",
        description: "",
        imageUrl: "",
      });
    } catch (error) {
      console.error("Error reporting incident:", error);
      alert("Failed to report incident.");
    }
  };

  return (
    <div className="form-container">
      <h2>Report an Incident</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Latitude</label>
          <input
            type="number"
            step="any"
            name="latitude"
            value={formData.latitude}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Longitude</label>
          <input
            type="number"
            step="any"
            name="longitude"
            value={formData.longitude}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Type</label>
          <select
            name="typeId"
            value={formData.typeId}
            onChange={handleChange}
            className="form-select"
            required
          >
            <option value="">Select type</option>
            {types.map((t) => (
              <option key={t.id} value={t.id}>
                {t.name}
              </option>
            ))}
          </select>
        </div>

        <div className="mb-3">
          <label className="form-label">Subtype</label>
          <select
            name="subtypeId"
            value={formData.subtypeId}
            onChange={handleChange}
            className="form-select"
          >
            <option value="">Select subtype</option>
            {subtypes.map((s) => (
              <option key={s.id} value={s.id}>
                {s.name}
              </option>
            ))}
          </select>
        </div>

        <div className="mb-3">
          <label className="form-label">Description</label>
          <textarea
            name="description"
            value={formData.description}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Image URL</label>
          <input
            type="text"
            name="imageUrl"
            value={formData.imageUrl}
            onChange={handleChange}
            className="form-control"
          />
        </div>

        <button type="submit" className="btn btn-primary mt-3">
          Submit Incident
        </button>
      </form>
    </div>
  );
}
