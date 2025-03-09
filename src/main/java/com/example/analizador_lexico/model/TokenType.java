package com.example.analizador_lexico.model;

/**
 * Enumeración de los tipos de token.
 * Se incluyen palabras reservadas, identificadores, literales, operadores, delimitadores y errores.
 */
public enum TokenType {
    // Palabras reservadas (ejemplos; agregar según las especificaciones)
    ENTERO, REAL, BOOLEANO, CARACTER, CADENA,
    IF, ELSE, FOR, WHILE, DO, RETURN, ESCRIBIR, ESCRIBIRLINEA, LONGITUD, ACADENA,
    
    // Identificador y literales
    IDENTIFIER, NUMERO, REAL_NUMBER, STRING, CHAR,
    
    // Operadores aritméticos y relacionales
    PLUS, MINUS, MULTIPLY, DIVIDE, POWER, MODULO,
    INCREMENT, DECREMENT, EQUALITY, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL, NOT_EQUAL,
    
    // Operadores lógicos
    LOGICAL_AND, LOGICAL_OR, LOGICAL_NOT,
    
    // Delimitadores y signos especiales
    SEMICOLON, COMMA, OPEN_PAREN, CLOSE_PAREN, OPEN_BRACE, CLOSE_BRACE,
    
    // Comentarios (se pueden manejar de forma separada o simplemente ignorarlos en la salida)
    COMMENT,
    
    // Error o token no reconocido
    ERROR,
    
    // Fin de archivo
    EOF
}
