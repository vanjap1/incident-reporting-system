import { useState, useContext } from "react";
import { login } from "../services/api";
import { AuthContext } from "../context/AuthContext";
import "../styles/forms.css";

export default function Login() {
  const [form, setForm] = useState({ username: "", password: "" });
  const { setToken } = useContext(AuthContext);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await login(form);
      const token = response.data.token;
      console.log("JWT token:", token);
      setToken(token);
      window.location.href = "/";
    } catch (err) {
      console.error("Login failed:", err);
    }
  };

  const handleGoogleLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/google";
  };

  return (
    <div className="form-container">
      <h2>Login</h2>
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
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
          className="form-control mt-2"
          required
        />

        <button type="submit" className="btn btn-primary mt-3">
          Login
        </button>
      </form>

      <hr />

      <button onClick={handleGoogleLogin} className="btn btn-danger mt-3">
        Sign in with Google
      </button>
    </div>
  );
}
