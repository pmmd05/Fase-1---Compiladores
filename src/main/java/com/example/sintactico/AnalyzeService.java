package com.example.sintactico;

import com.example.sintactico.dto.AnalyzeRequest;
import com.example.sintactico.dto.AnalyzeResponse;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Para escribir archivos
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
            return new AnalyzeResponse(errors, Map.of(), null);
        }

        // 6. Evalúo con Visitor
        EvalVisitor visitor = new EvalVisitor();
        visitor.visit(tree);

        // 7. Recupero resultados y devuelvo
        Map<String, Double> results = visitor.getMemory();  // asume que expos getMemory()


        IntermediateCodeVisitor icv = new IntermediateCodeVisitor();
        icv.visit(tree);
        List<String> ir = icv.getInstructions();

        // 3. Guarda en disco (por ejemplo en /tmp o en una carpeta de recursos)
        //    Genera un nombre único:
        String filename = "intermediate_" + System.currentTimeMillis() + ".txt";
        Path outPath = Path.of("generated_ir", filename);
        // Asegúrate de que exista el directorio:
        try {
            Files.createDirectories(outPath.getParent());
            Files.write(outPath, ir, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // registra el error y decides si devuelves response con irFileName=null
            e.printStackTrace();
            errors.add("Error al escribir IR: " + e.getMessage());
            return new AnalyzeResponse(errors, Map.of(), null);
        }
        // 4. En tu AnalyzeResponse añade un campo adicional, p.ej. irFileName o irUrl
        AnalyzeResponse response = new AnalyzeResponse(errors, results, filename);
        response.setIrFileName(filename);

        return new AnalyzeResponse(errors, results, filename);

    }
}