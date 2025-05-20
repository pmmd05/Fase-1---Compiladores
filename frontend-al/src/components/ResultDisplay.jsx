import React from 'react';

const ResultDisplay = ({ results }) => {
  return (
    <div>
      <h2>Resultados</h2>
      {results && Object.keys(results).length > 0 ? (
        <table border="1" cellPadding="5">
          <thead>
            <tr>
              <th>Variable</th>
              <th>Valor</th>
            </tr>
          </thead>
          <tbody>
            {Object.entries(results).map(([name, value]) => (
              <tr key={name}>
                <td>{name}</td>
                <td>{value}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No hay resultados para mostrar.</p>
      )}
    </div>
  );
};

export default ResultDisplay;
