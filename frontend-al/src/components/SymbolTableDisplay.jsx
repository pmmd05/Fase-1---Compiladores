// src/components/SymbolTableDisplay.jsx
import React from 'react';

const SymbolTableDisplay = ({ symbolTable }) => {
  return (
    <div>
      <h2>Tabla de Símbolos</h2>
      {symbolTable && symbolTable.length > 0 ? (
        <table border="1" cellPadding="5">
          <thead>
            <tr>
              <th>Identificador</th>
              <th>Tipo</th>
              <th>Línea</th>
              <th>Columna</th>
            </tr>
          </thead>
          <tbody>
            {symbolTable.map((token, index) => (
              <tr key={index}>
                <td>{token.lexeme}</td>
                <td>{token.type}</td>
                <td>{token.line}</td>
                <td>{token.column}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No hay símbolos en la tabla.</p>
      )}
    </div>
  );
};

export default SymbolTableDisplay;
