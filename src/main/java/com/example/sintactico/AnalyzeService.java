package com.example.sintactico;

import com.example.sintactico.dto.AnalyzeRequest;
import com.example.sintactico.dto.AnalyzeResponse;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AnalyzeService {

    public AnalyzeResponse analyze(AnalyzeRequest req) {
        List<String> errors = new ArrayList<>();
        // 1. CharStream
        CharStream input = CharStreams.fromString(req.getCode());

        // 2. Lexer + recolección de errores
        gramaticaLexer lexer = new gramaticaLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new ErrorCollectorListener(errors));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 3. Parser + recolección de errores
        gramaticaParser parser = new gramaticaParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new ErrorCollectorListener(errors));

        // 4. Parse
        ParseTree tree = parser.prog();

        // 5. Si hay errores sintácticos, devuelvo sólo errores
        if (!errors.isEmpty()) {
            return new AnalyzeResponse(errors, Map.of());
        }

        // 6. Evalúo con Visitor
        EvalVisitor visitor = new EvalVisitor();
        visitor.visit(tree);

        // 7. Recupero resultados y devuelvo
        Map<String, Double> results = visitor.getMemory();  // asume que expos getMemory()
        return new AnalyzeResponse(errors, results);
    }
}
