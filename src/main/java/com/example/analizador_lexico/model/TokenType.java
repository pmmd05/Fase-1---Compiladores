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
    IDENTIFICADOR, NUMERO, REAL_NUMBER, STRING, CHAR,
    
    // Operadores aritméticos y relacionales
    SUMA, MENOS, MULTIPLICAR, DIVIDIR, POTENCIA, MODULO,
    INCREMENTO, DECREMENTO, ASIGNADOR, MENOR_QUE, MAYOR_QUE, MAYOR_IGUAL, MENOR_IGUAL, NO_IGUAL, IGUALDAD,
    
    // Operadores lógicos
    AND, OR, NOT,
    
    // Delimitadores y signos especiales
    PUNTO_COMA, COMA, PAREN_AB, PAREN_CER, LLAVE_AB, LLAVE_CER,
    
    // Comentarios (se pueden manejar de forma separada o simplemente ignorarlos en la salida)
    COMENTARIO,
    
    // Error o token no reconocido
    ERROR,
    
    // Fin de archivo
    EOF
}
