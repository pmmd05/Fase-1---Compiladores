// src/App.jsx
import React, { useState } from 'react';
import CodeEditor from './components/CodeEditor';
import FileUploader from './components/FileUploader';
import ErrorDisplay from './components/ErrorDisplay';
import ResultDisplay from './components/ResultDisplay';
import './App.css';

const App = () => {
  const [code, setCode] = useState('');
  const [errors, setErrors] = useState([]);
  const [results, setResults] = useState({});
  const [irFileName, setIrFileName] = useState(null);

  const handleAnalyze = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/analyze', { 
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ code })
      });
      const data = await response.json();
      setErrors(data.errors || []);
      setResults(data.results || {});
      setIrFileName(data.irFileName || null);
    } catch (error) {
      setErrors(['Error al conectar con el servidor.']);
      setResults({});
      setIrFileName(null);
    }
  };



  return (
    <div>
      <h1 className="title">Proyecto Fase 2</h1>
      <img 
        className="image-top-left" 
        src="https://futurolandivariano.url.edu.gt/quickwebsite/img/brand/logo-azul.png" 
        alt="Logo Azul" 
      />

      <div class="top-rigth">
        <p class="textio">Proyecto Compiladores</p>
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
            <ResultDisplay results={results} />
        </div>
        { irFileName && (
        <div style={{ marginTop: '1em' }}>
          <a
            href={`http://localhost:8080/api/ir/${irFileName}`}
            download
            className="analyze-button"
          >
            📄 Descargar Código Intermedio
          </a>
        </div>
        )}
      </div>
    </div>
  );
};

export default App;