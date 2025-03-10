import React from 'react';

const TokenDisplay = ({ tokens }) => {
  return (
    <div>
      <h2>Tokens Generados</h2>
      {tokens && tokens.length > 0 ? (
        <table border="1" cellPadding="5">
          <thead>
            <tr>
              <th>Tipo</th>
              <th>Lexema</th>
              <th>Línea</th>
              <th>Columna</th>
            </tr>
          </thead>
          <tbody>
            {tokens.map((token, index) => (
              <tr key={index}>
                <td>{token.type}</td>
                <td>{token.lexeme}</td>
                <td>{token.line}</td>
                <td>{token.column}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No se han generado tokens.</p>
      )}
    </div>
  );
};

export default TokenDisplay;
