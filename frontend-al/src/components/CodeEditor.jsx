import React from 'react';

const CodeEditor = ({ code, setCode }) => {
  return (
    <div>
      <h2>Código fuente</h2>
      <textarea
        value={code}
        onChange={(e) => setCode(e.target.value)}
        rows="10"
        cols="80"
        placeholder="Escribe tu código aquí..."
      />
    </div>
  );
};

export default CodeEditor;
