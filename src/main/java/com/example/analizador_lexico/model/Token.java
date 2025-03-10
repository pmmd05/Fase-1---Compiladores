package com.example.analizador_lexico.model;

/**
 * Clase que representa un token generado por el analizador léxico.
 */
public class Token {
    private TokenType type;
    private String lexeme;
    private int line;
    private int column;

    // Constructor sin argumentos (opcional, para la deserialización)
    public Token() {
    }

    public Token(TokenType type, String lexeme, int line, int column) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    // Getters y setters
    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format("Token { Tipo: %s, Lexema: '%s', Línea: %d, Columna: %d }", 
                              type, lexeme, line, column);
    }
}
