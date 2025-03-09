package com.example.analizador_lexico.model;

/**
 * Clase que representa un token generado por el analizador léxico.
 */
public class Token {
    private TokenType type;
    private String lexeme;
    private int line;
    private int column;

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

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return String.format("Token { Tipo: %s, Lexema: '%s', Línea: %d, Columna: %d }", 
                              type, lexeme, line, column);
    }
}
