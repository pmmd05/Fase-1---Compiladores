package com.example.analizador_lexico.table;

import com.example.analizador_lexico.model.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Tabla de símbolos que almacena los identificadores encontrados.
 */
public class Tabla {
    private List<Token> symbols;

    public Tabla() {
        symbols = new ArrayList<>();
    }

    public void add(Token token) {
        // Evitar duplicados (según sea necesario)
        symbols.add(token);
    }

    public List<Token> getSymbols() {
        return symbols;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tabla de Símbolos:\n");
        sb.append(String.format("%-15s %-10s %-6s %-6s\n", "Identificador", "Token", "Línea", "Columna"));
        for (Token token : symbols) {
            sb.append(String.format("%-15s %-10s %-6d %-6d\n", 
                    token.getLexeme(), token.getType(), token.getLine(), token.getColumn()));
        }
        return sb.toString();
    }
}
