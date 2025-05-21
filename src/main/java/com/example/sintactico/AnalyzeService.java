package com.example.sintactico;

import com.example.sintactico.dto.AnalyzeRequest;
import com.example.sintactico.dto.AnalyzeResponse;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class AnalyzeService {

    public AnalyzeResponse analyze(AnalyzeRequest req) {
        List<String> errors = new ArrayList<>();

        // 1. CharStream
        CharStream input = CharStreams.fromString(req.getCode());

        // 2. Lexer + recolecta errores léxicos
        gramaticaLexer lexer = new gramaticaLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new ErrorCollectorListener(errors));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 3. Parser + recolecta errores sintácticos
        gramaticaParser parser = new gramaticaParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new ErrorCollectorListener(errors));

        // 4. Parse
        ParseTree tree = parser.prog();

         // 5. (NO DETENERSE AQUÍ) marcamos si hubo errores sintácticos o léxicos
        boolean huboErroresSintacticos = !errors.isEmpty();

         // 6. PASE SEMÁNTICO: detecta variables no definidas
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new SemanticListener(errors), tree);

       // 7. Si hubo cualquier tipo de error (léxico, sintáctico o semántico), devolvemos
        if (!errors.isEmpty()) {
            return new AnalyzeResponse(errors, Map.of(), null);
        }

        // 8. Evalúo con Visitor (ya sin errores)
        EvalVisitor visitor = new EvalVisitor();
        visitor.visit(tree);
        Map<String, Double> results = visitor.getMemory();

        // 9. Generación de código intermedio
        IntermediateCodeVisitor icv = new IntermediateCodeVisitor();
        icv.visit(tree);
        List<String> ir = icv.getInstructions();

        // 10. Guarda en disco
        String filename = "intermediate_" + System.currentTimeMillis() + ".txt";
        Path outPath = Path.of("generated_ir", filename);
        try {
            Files.createDirectories(outPath.getParent());
            Files.write(outPath, ir, StandardCharsets.UTF_8);
        } catch (IOException e) {
            errors.add("Error al escribir IR: " + e.getMessage());
        }

        // 11. Devuelvo todo (errors estará vacío salvo fallo de IO)
        return new AnalyzeResponse(errors, results, filename);
    }
}
