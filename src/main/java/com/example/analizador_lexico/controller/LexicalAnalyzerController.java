package com.example.analizador_lexico.controller;

import com.example.analizador_lexico.analyzer.LexicalAnalyzer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (útil para desarrollo con React)
public class LexicalAnalyzerController {

    @PostMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzeCode(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");
        LexicalAnalyzer analyzer = new LexicalAnalyzer(code);
        analyzer.analyze();

        Map<String, Object> result = new HashMap<>();
        result.put("tokens", analyzer.getTokens());
        result.put("errors", analyzer.getErrors());
        // Se extrae la lista de tokens de la tabla de símbolos (asumiendo que Tabla.getSymbols() devuelve List<Token>)
        result.put("symbolTable", analyzer.getSymbolTable().getSymbols());

        return ResponseEntity.ok(result);
    }
}
