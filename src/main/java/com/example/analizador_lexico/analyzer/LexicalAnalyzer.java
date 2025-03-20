package com.example.analizador_lexico.analyzer;

import com.example.analizador_lexico.model.Token;
import com.example.analizador_lexico.model.TokenType;
import com.example.analizador_lexico.table.Tabla;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que realiza el análisis léxico sobre el código fuente utilizando expresiones regulares separadas
 * y validaciones adicionales para detectar errores léxicos (por ejemplo, literales sin cerrar, secuencias de escape inválidas, etc.).
 */
public class LexicalAnalyzer {
    private String input;
    private int pos;
    private List<Token> tokens;
    private List<String> errors;
    private Tabla symbolTable;
    
    // Mapa de palabras reservadas (en minúsculas para facilitar la insensibilidad a mayúsculas)
    private Map<String, TokenType> palabrasReservadas;
    
    /*
     * Nota sobre las expresiones:
     * - Se usa "^" para forzar la coincidencia al inicio de la subcadena.
     * - Para los literales de cadena y de carácter se han definido patrones que sólo permiten las secuencias de escape:
     *     \" (comilla doble), \n, \t, \r, \b, \f y \\.
     * - El patrón para números incluye un lookahead negativo para evitar que se unan con letras.
     */
    private static final String ESPACIOBLANCO    = "^\\s+";
    private static final String COMENTARIOLINEA = "^//.*"; // Hasta fin de línea
    // Comentario multilínea: si no se encuentra "*/" se tratará luego como error
    private static final String COMENTARIOMULTILINEA  = "^/\\*(?:.|\\n|\\r)*?\\*/";
    // Literal de cadena: debe iniciar y cerrar con comillas dobles y solo permite escapes válidos
    private static final String CADENA         = "^\"((\\\\[\"ntbrf])|[^\"\\\\])*\"";
    // Literal de carácter: inicia y cierra con comilla simple
    private static final String CARACTER           = "^'((\\\\[\"ntbrf])|[^'\\\\])'";
    // Número: entero o real; se utiliza lookahead negativo para evitar que se una a letras o guiones bajos
    private static final String NUMERO         = "^(\\d+\\.\\d+|\\d+)(?![A-Za-z_])";
    // Identificador: debe iniciar con letra o guión bajo
    private static final String IDENTIFICADOR     = "^[A-Za-z_][A-Za-z0-9_]*";
    // Operadores (compuestos y simples)
    private static final String OPERADOR       = "^(\\+\\+|--|==|>=|<=|!=|&&|\\|\\||\\+|-|\\*|/|\\^|#|=|>|<|!)";
    // Delimitadores: ; , ( ) { }
    private static final String DELIMITADOR      = "^[;,(){}]";

    // Compilamos los patrones
    private final Pattern espacioblanco = Pattern.compile(ESPACIOBLANCO);
    private final Pattern comentariolinea = Pattern.compile(COMENTARIOLINEA);
    private final Pattern comentariomulti  = Pattern.compile(COMENTARIOMULTILINEA);
    private final Pattern cadena = Pattern.compile(CADENA);
    private final Pattern caracter = Pattern.compile(CARACTER);
    private final Pattern numero = Pattern.compile(NUMERO);
    private final Pattern identificador = Pattern.compile(IDENTIFICADOR);
    private final Pattern operador = Pattern.compile(OPERADOR);
    private final Pattern delimitador     = Pattern.compile(DELIMITADOR);
    
    public LexicalAnalyzer(String input) {
        this.input = input;
        this.pos = 0;
        this.tokens = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.symbolTable = new Tabla();
        initpalabrasReservadas();
    }
    
    // Inicializa el mapa de palabras reservadas
    private void initpalabrasReservadas() {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("entero", TokenType.ENTERO);
        palabrasReservadas.put("real", TokenType.REAL);
        palabrasReservadas.put("booleano", TokenType.BOOLEANO);
        palabrasReservadas.put("caracter", TokenType.CARACTER);
        palabrasReservadas.put("cadena", TokenType.CADENA);
        palabrasReservadas.put("if", TokenType.IF);
        palabrasReservadas.put("else", TokenType.ELSE);
        palabrasReservadas.put("for", TokenType.FOR);
        palabrasReservadas.put("while", TokenType.WHILE);
        palabrasReservadas.put("do", TokenType.DO);
        palabrasReservadas.put("return", TokenType.RETURN);
        palabrasReservadas.put("escribir", TokenType.ESCRIBIR);
        palabrasReservadas.put("escribirlinea", TokenType.ESCRIBIRLINEA);
        palabrasReservadas.put("longitud", TokenType.LONGITUD);
        palabrasReservadas.put("acadena", TokenType.ACADENA);
        // Agregar más según el lenguaje
    }
    
    /**
     * Método principal que realiza el análisis léxico utilizando expresiones regulares separadas.
     * Se incluyen validaciones adicionales para detectar errores en cadenas sin cerrar, comentarios no cerrados,
     * y números o identificadores mal formados.
     */
    public void analyze() {
        while (pos < input.length()) {
            String remaining = input.substring(pos);
            int[] posInfo = getLineAndColumn(pos);
            
            // 1. Espacios en blanco (se consumen y se ignoran)
            Matcher m = espacioblanco.matcher(remaining);
            if (m.find()) {
                pos += m.end();
                continue;
            }
            
            // 2. Comentario de línea
            m = comentariolinea.matcher(remaining);
            if (m.find()) {
                String lexeme = m.group();
                tokens.add(new Token(TokenType.COMENTARIO, lexeme, posInfo[0], posInfo[1]));
                pos += lexeme.length();
                continue;
            }
            
            // 3. Comentario multilínea
            if (remaining.startsWith("/*")) {
                m = comentariomulti.matcher(remaining);
                if (m.find()) {
                    String lexeme = m.group();
                    tokens.add(new Token(TokenType.COMENTARIO, lexeme, posInfo[0], posInfo[1]));
                    pos += lexeme.length();
                } else {
                    // No se encontró el cierre del comentario
                    errors.add(String.format("Error léxico en línea %d, columna %d: comentario multilínea sin cerrar.", posInfo[0], posInfo[1]));
                    tokens.add(new Token(TokenType.ERROR, remaining, posInfo[0], posInfo[1]));
                    pos = input.length();
                }
                continue;
            }
            
            // 4. Literal de cadena
            if (remaining.startsWith("\"")) {
                m = cadena.matcher(remaining);
                if (m.find()) {
                    String lexeme = m.group();
                    // Remover las comillas de apertura y cierre
                    String contenido = lexeme.substring(1, lexeme.length() - 1);
                    tokens.add(new Token(TokenType.STRING, contenido, posInfo[0], posInfo[1]));
                    pos += lexeme.length();
                } else {
                    // Si inicia con comilla pero no se cierra, se reporta error de cadena sin cerrar
                    errors.add(String.format("Error léxico en línea %d, columna %d: cadena sin cerrar.", posInfo[0], posInfo[1]));
                    tokens.add(new Token(TokenType.ERROR, remaining, posInfo[0], posInfo[1]));
                    pos = input.length();
                }
                continue;
            }
            
            // 5. Literal de carácter
            if (remaining.startsWith("'")) {
                m = caracter.matcher(remaining);
                if (m.find()) {
                    String lexeme = m.group();
                    String contenido = lexeme.substring(1, lexeme.length() - 1);
                    tokens.add(new Token(TokenType.CHAR, contenido, posInfo[0], posInfo[1]));
                    pos += lexeme.length();
                } else {
                    errors.add(String.format("Error léxico en línea %d, columna %d: literal de carácter mal formado.", posInfo[0], posInfo[1]));
                    tokens.add(new Token(TokenType.ERROR, remaining.substring(0, 1), posInfo[0], posInfo[1]));
                    pos++;
                }
                continue;
            }
            
            // 6. Número (entero o real); se evita que se unan a letras con lookahead negativo
            m = numero.matcher(remaining);
            if (m.find()) {
                String lexeme = m.group();
                TokenType type = lexeme.contains(".") ? TokenType.REAL_NUMBER : TokenType.NUMERO;
                tokens.add(new Token(type, lexeme, posInfo[0], posInfo[1]));
                pos += lexeme.length();
                continue;
            }
            
            // 7. Identificador (o palabra reservada)
            m = identificador.matcher(remaining);
            if (m.find()) {
                String lexeme = m.group();
                String lexemeLower = lexeme.toLowerCase();
                TokenType type = palabrasReservadas.getOrDefault(lexemeLower, TokenType.IDENTIFICADOR);
                tokens.add(new Token(type, lexeme, posInfo[0], posInfo[1]));
                if (type == TokenType.IDENTIFICADOR) {
                    symbolTable.add(new Token(TokenType.IDENTIFICADOR, lexeme, posInfo[0], posInfo[1]));
                }
                pos += lexeme.length();
                continue;
            }
            
            // 8. Operador
            m = operador.matcher(remaining);
            if (m.find()) {
                String lexeme = m.group();
                TokenType type = getOperatorTokenType(lexeme);
                tokens.add(new Token(type, lexeme, posInfo[0], posInfo[1]));
                pos += lexeme.length();
                continue;
            }
            
            // 9. Delimitador
            m = delimitador.matcher(remaining);
            if (m.find()) {
                String lexeme = m.group();
                TokenType type = getDelimiterTokenType(lexeme);
                tokens.add(new Token(type, lexeme, posInfo[0], posInfo[1]));
                pos += lexeme.length();
                continue;
            }
            
            // Si ningún patrón coincide, se marca error y se consume un carácter.
            String errorChar = remaining.substring(0, 1);
            errors.add(String.format("Error léxico en línea %d, columna %d: carácter no reconocido: '%s'", posInfo[0], posInfo[1], errorChar));
            tokens.add(new Token(TokenType.ERROR, errorChar, posInfo[0], posInfo[1]));
            pos++;
        }
        // Agregar token de fin de archivo
        int[] eofPos = getLineAndColumn(input.length());
        tokens.add(new Token(TokenType.EOF, "", eofPos[0], eofPos[1]));
    }
    
    /**
     * Calcula la línea y columna a partir de una posición en el input.
     * @param pos Posición en el input.
     * @return Un arreglo de dos enteros: [línea, columna].
     */
    private int[] getLineAndColumn(int pos) {
        int line = 1;
        int lastNewline = -1;
        for (int i = 0; i < pos; i++) {
            if (input.charAt(i) == '\n') {
                line++;
                lastNewline = i;
            }
        }
        int column = pos - lastNewline;
        return new int[]{line, column};
    }
    
    /**
     * Determina el tipo de token para un operador dado.
     * @param op Operador extraído.
     * @return TokenType correspondiente.
     */
    private TokenType getOperatorTokenType(String op) {
        switch(op) {
            case "++": return TokenType.INCREMENTO;
            case "--": return TokenType.DECREMENTO;
            case "==": return TokenType.IGUALDAD;
            case ">=": return TokenType.MAYOR_IGUAL;
            case "<=": return TokenType.MENOR_IGUAL;
            case "!=": return TokenType.NO_IGUAL;
            case "&&": return TokenType.AND;
            case "||": return TokenType.OR;
            case "+":  return TokenType.SUMA;
            case "-":  return TokenType.MENOS;
            case "*":  return TokenType.MULTIPLICAR;
            case "/":  return TokenType.DIVIDIR;
            case "^":  return TokenType.POTENCIA;
            case "#":  return TokenType.MODULO;
            case "=":  return TokenType.ASIGNADOR; // Se podría definir otro tipo, por ejemplo ASSIGN
            case ">":  return TokenType.MAYOR_QUE;
            case "<":  return TokenType.MENOR_QUE;
            case "!":  return TokenType.NOT;
            default:   return TokenType.ERROR;
        }
    }
    
    /**
     * Determina el tipo de token para un delimitador dado.
     * @param delim Delimitador extraído.
     * @return TokenType correspondiente.
     */
    private TokenType getDelimiterTokenType(String delim) {
        switch(delim) {
            case ";": return TokenType.PUNTO_COMA;
            case ",": return TokenType.COMA;
            case "(": return TokenType.PAREN_AB;
            case ")": return TokenType.PAREN_CER;
            case "{": return TokenType.LLAVE_AB;
            case "}": return TokenType.LLAVE_CER;
            default:  return TokenType.ERROR;
        }
    }
    
    // Getters para obtener los resultados del análisis
    public List<Token> getTokens() {
        return tokens;
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public Tabla getSymbolTable() {
        return symbolTable;
    }
}