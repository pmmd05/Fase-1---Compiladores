package com.example.analizador_lexico;

import com.example.analizador_lexico.analyzer.LexicalAnalyzer;
import com.example.analizador_lexico.model.Token;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalizadorLexicoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AnalizadorLexicoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Ejemplo de código fuente a analizar (puede ser reemplazado por la entrada del usuario)
        String sourceCode = "int contador;\n"
                + "1contador = 10;\n"
                + "If (contador == 10) {\n"
                + "   EscribirLinea(\"¡Correcto!\");\n"
                + "} else {\n"
                + "   EscribirLinea(\"Error\");\n"
                + "}\n"
                + "// Este es un comentario\n"
                + "/* Comentario\n de varias lineas */ \n"
				+ "double x = 2.11;\n" ;

        LexicalAnalyzer lexer = new LexicalAnalyzer(sourceCode);
        lexer.analyze();

        // Imprimir tokens
        System.out.println("=== Tokens generados ===");
        for (Token token : lexer.getTokens()) {
            System.out.println(token);
        }

        // Imprimir errores léxicos (si existen)
        if (!lexer.getErrors().isEmpty()) {
            System.out.println("\n=== Errores léxicos ===");
            for (String error : lexer.getErrors()) {
                System.out.println(error);
            }
        }

        // Imprimir tabla de símbolos
        System.out.println("\n" + lexer.getSymbolTable().toString());
    }
}
