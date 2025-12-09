import "../styles/alert.css"; // optional styling

export default function Alert({ type = "info", message, onClose }) {
  if (!message) return null;

  const alertClass = {
    success: "alert alert-success",
    error: "alert alert-danger",
    info: "alert alert-info",
    warning: "alert alert-warning",
  }[type];

  return (
    <div className={alertClass}>
      <span>{message}</span>
      {onClose && (
        <button className="close-btn" onClick={onClose}>
          ×
        </button>
      )}
    </div>
  );
}
