import React from 'react';

const ErrorDisplay = ({ errors }) => {
  return (
    <div>
      <h2>Errores detectados</h2>
      {errors.length > 0 ? (
        <ul>
          {errors.map((err, idx) => (
            <li key={idx}>{err}</li>
          ))}
        </ul>
      ) : (
        <p>No se han detectado errores.</p>
      )}
    </div>
  );
};

export default ErrorDisplay;
