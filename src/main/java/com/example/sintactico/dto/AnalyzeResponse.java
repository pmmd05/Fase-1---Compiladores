package com.example.sintactico.dto;

import java.util.List;
import java.util.Map;

public class AnalyzeResponse {
    private List<String> errors;
    private Map<String, Double> results;
    public AnalyzeResponse() {}
    public AnalyzeResponse(List<String> errors, Map<String,Double> results) {
        this.errors = errors;
        this.results = results;
    }
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
    public Map<String, Double> getResults() { return results; }
    public void setResults(Map<String, Double> results) { this.results = results; }
    
}
