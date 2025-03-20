// src/App.jsx
import React, { useState } from 'react';
import CodeEditor from './components/CodeEditor';
import FileUploader from './components/FileUploader';
import ErrorDisplay from './components/ErrorDisplay';
import TokenDisplay from './components/TokenDisplay';
import SymbolTableDisplay from './components/SymbolTableDisplay';

const App = () => {
  const [code, setCode] = useState('');
  const [errors, setErrors] = useState([]);
  const [tokens, setTokens] = useState([]);
  const [symbolTable, setSymbolTable] = useState([]);

  const handleAnalyze = async () => {
    try {
      const response = await fetch('/api/analyze', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ code })
      });
      const data = await response.json();
      setErrors(data.errors || []);
      setTokens(data.tokens || []);
      setSymbolTable(data.symbolTable || []);
    } catch (error) {
      setErrors(['Error al conectar con el servidor.']);
    }
  };

  return (
    <div>
      <h1>Analizador Léxico</h1>
      <CodeEditor code={code} setCode={setCode} />
      <FileUploader setCode={setCode} />
      <button onClick={handleAnalyze}>Analizar Código</button>
      <ErrorDisplay errors={errors} />
      <TokenDisplay tokens={tokens} />
      <SymbolTableDisplay symbolTable={symbolTable} />
    </div>
  );
};

export default App;