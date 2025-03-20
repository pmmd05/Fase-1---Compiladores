// src/App.jsx
import React, { useState } from 'react';
import CodeEditor from './components/CodeEditor';
import FileUploader from './components/FileUploader';
import ErrorDisplay from './components/ErrorDisplay';
import TokenDisplay from './components/TokenDisplay';
import SymbolTableDisplay from './components/SymbolTableDisplay';
import './App.css';

const App = () => {
  const [code, setCode] = useState('');
  const [errors, setErrors] = useState([]);
  const [tokens, setTokens] = useState([]);
  const [symbolTable, setSymbolTable] = useState([]);

  const handleAnalyze = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/analyze', { 
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
      <h1 className="title">Analizador Léxico</h1>
      <img 
        className="image-top-left" 
        src="https://futurolandivariano.url.edu.gt/quickwebsite/img/brand/logo-azul.png" 
        alt="Logo Azul" 
      />

      <div class="top-rigth">
        <p class="textio">Fase 1 - Proyecto Compiladores</p>
      </div>


      <div className="container">        
        {/* Panel Izquierdo */}
        <div className="panel left-panel">
          <div className="section">
            <CodeEditor code={code} setCode={setCode} />
          </div>
          <div className="section">
            <FileUploader setCode={setCode} />
            <button className="analyze-button" onClick={handleAnalyze}>
              Analizar Código
            </button>
          </div>
          <div>
            <ErrorDisplay errors={errors} />
          </div>
        </div>

        {/* Panel Derecho */}
        <div className="panel right-panel">
          <div className="section">
            <TokenDisplay tokens={tokens} />
          </div>
          <div>
            <SymbolTableDisplay symbolTable={symbolTable} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default App;
