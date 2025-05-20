package com.example.sintactico;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class main {
    public static class VerboseListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object offendingSymbol,
                                int line, int charPositionInLine,
                                String msg,
                                RecognitionException e) {
            System.err.printf(">> Error sintáctico en línea %d columna %d: %s%n",
                              line, charPositionInLine, msg);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Prueba de Analizador Algebraico (escribe 'exit' para salir)");
        String line;
        while (true) {
            System.out.print("> ");
            line = reader.readLine();
            if (line == null || line.trim().equalsIgnoreCase("exit")) {
                System.out.println("Saliendo.");
                break;
            }
            // 1. Crea un CharStream desde la línea leída
            CharStream input = CharStreams.fromString(line);

            // 2. Crea el lexer y captura errores
            gramaticaLexer lexer = new gramaticaLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 3. Crea el parser y registra el listener de errores
            gramaticaParser parser = new gramaticaParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new VerboseListener());

            // 4. Invoca la regla de entrada (prog)
            ParseTree tree = parser.prog();

            // 5. Muestra el árbol sintáctico en forma LISP
            System.out.println(tree.toStringTree(parser));

            // 6. Visita el árbol sintáctico
            EvalVisitor evaluator = new EvalVisitor();
            evaluator.visit(tree);
        }
    }
}
