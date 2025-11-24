import { useState } from "react";
import "./../styles/common.css";

export default function ReportIncident() {
  const [form, setForm] = useState({
    title: "",
    description: "",
    latitude: "",
    longitude: "",
  });

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await fetch("http://localhost:8085/api/incidents", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    });
    const data = await res.json();
    console.log("Incident reported:", data);
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2>Report Incident</h2>
        <form className="auth-form" onSubmit={handleSubmit}>
          <input name="title" placeholder="Title" onChange={handleChange} />
          <textarea
            name="description"
            placeholder="Description"
            onChange={handleChange}
          />
          <input
            name="latitude"
            placeholder="Latitude"
            onChange={handleChange}
          />
          <input
            name="longitude"
            placeholder="Longitude"
            onChange={handleChange}
          />
          <button type="submit">Submit Incident</button>
        </form>
      </div>
    </div>
  );
}
