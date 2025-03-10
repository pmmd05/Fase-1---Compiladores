import React from 'react';

const FileUploader = ({ setCode }) => {
  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = (event) => {
      setCode(event.target.result);
    };
    reader.readAsText(file);
  };

  return (
    <div>
      <h2>Subir archivo</h2>
      <input type="file" accept=".txt,.code" onChange={handleFileChange} />
    </div>
  );
};

export default FileUploader;
