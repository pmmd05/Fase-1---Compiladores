import React from 'react';

const CodeEditor = ({ code, setCode }) => {
  return (
    <div>
      <h2>Código fuente</h2>
      <textarea
       className="code-editor"
       value={code}
       onChange={(e) => setCode(e.target.value)}
       placeholder="Escribe tu código aquí..."
      />
    </div>
  );
};

export default CodeEditor;
