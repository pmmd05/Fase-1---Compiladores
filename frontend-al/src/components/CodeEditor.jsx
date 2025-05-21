// src/components/CodeEditor.jsx
import React from 'react';
import AceEditor from 'react-ace';

// Modo y tema; ajusta según tu gramática (aquí usamos Java como ejemplo)
import 'ace-builds/src-noconflict/mode-java'; //Importamos el lenguaje de programación que deseamos utilizar en este caso java
import "ace-builds/src-noconflict/theme-twilight"; //Importamos el tema de twilight para el CodeEditor

const CodeEditor = ({ code, setCode }) => {
  return (
    <div>
      <h2>Código fuente</h2>
      <AceEditor
        mode="java"
        theme="twilight"
        name="code-editor"
        width="100%"
        height="200px"
        fontSize={14}
        value={code}
        onChange={(value) => setCode(value)}
        setOptions={{
          showLineNumbers: true,
          tabSize: 2,
        }}
        editorProps={{ $blockScrolling: true }}
      />
    </div>
  );
};

export default CodeEditor;
