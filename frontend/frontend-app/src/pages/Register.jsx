import React, { useState } from "react";
import "../styles/forms.css";
import Alert from "../components/Alert"; // import reusable alert
import { register } from "../services/api"; // your backend call

export default function Register() {
  const [form, setForm] = useState({ username: "", email: "", password: "" });
  const [alert, setAlert] = useState({ type: "", message: "" });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await register(form); // call backend
      setAlert({ type: "success", message: "Registration successful!" });
      console.log("Register submitted:", response.data);
      setTimeout(() => (window.location.href = "/login"), 1500);
    } catch (err) {
      const errorMsg =
        err.response?.data?.message || "Registration failed. Please try again.";
      setAlert({ type: "error", message: errorMsg });
      console.error("Registration error:", err);
    }
  };

  return (
    <div className="form-container">
      <h2>Register</h2>

      <Alert
        type={alert.type}
        message={alert.message}
        onClose={() => setAlert({ type: "", message: "" })}
      />

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          placeholder="Username"
          value={form.username}
          onChange={handleChange}
          className="form-control"
          required
        />

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
          className="form-control mt-2"
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
          className="form-control mt-2"
          required
        />

        <button type="submit" className="btn btn-primary mt-3">
          Register
        </button>
      </form>
    </div>
  );
}
