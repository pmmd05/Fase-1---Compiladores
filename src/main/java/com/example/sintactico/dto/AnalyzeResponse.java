package com.example.sintactico.dto;

import java.util.List;
import java.util.Map;

public class AnalyzeResponse {
    private List<String> errors;
    private Map<String, Double> results;
    public String irFileName;
    public AnalyzeResponse() {}
    public AnalyzeResponse(List<String> errors, Map<String,Double> results, String irFileName) {
        this.errors = errors;
        this.results = results;
        this.irFileName = irFileName;
    }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
    public Map<String, Double> getResults() { return results; }
    public void setResults(Map<String, Double> results) { this.results = results; }
    public String getIrFileName() { return irFileName; }
    public void setIrFileName(String irFileName) { this.irFileName = irFileName; }
    
}