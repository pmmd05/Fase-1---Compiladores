package com.example.sintactico;

import org.antlr.v4.runtime.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ErrorCollectorListener extends BaseErrorListener {
    private final List<String> errors;
    public ErrorCollectorListener(List<String> errors) {
        this.errors = errors;
        
    }
    private String traducirMensaje(String msg) {
        // Map de tokens a nombres en español
        Map<String, String> nombres = new LinkedHashMap<>();
        nombres.put("ID", "identificador");
        nombres.put("NUMBER", "número");
        nombres.put("'-'", "signo '-'" );
        nombres.put("'('", "paréntesis '('");
        nombres.put("')'", "paréntesis ')'");
        nombres.put("','", "coma");
        // …añade más si los usas…

        // 1) sustituir frases comunes
        if (msg.contains("mismatched input")) {
            msg = msg.replace("mismatched input", "entrada no coincidente");
        } else if (msg.contains("missing")) {
            msg = msg.replace("missing", "falta");
        } else if (msg.contains("no viable alternative")) {
            msg = "alternativa no viable";
        } else if (msg.contains("extraneous input")) {
            msg = msg.replace("extraneous input", "entrada extra");
        }

        // 2) traducir la parte de “expecting {...}”
        if (msg.contains("expecting")) {
            String[] parts = msg.split("expecting", 2);
            String antes   = parts[0].trim();
            String espera  = parts[1].trim()
                                  .replaceAll("[\\{\\}]", "");  // quita llaves
            // remplaza los nombres de tokens por español
            for (var e : nombres.entrySet()) {
                espera = espera.replace(e.getKey(), e.getValue());
            }
            return antes + ". Se esperaba: " + espera;
        }

        return msg;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg,
                            RecognitionException e) {

        String tokenText = offendingSymbol instanceof Token ? ((Token) offendingSymbol).getText() : "símbolo desconocido";

        String errorMsg = String.format(
            "Error de sintaxis en la línea %d, columna %d: cerca de '%s'. Descripción: %s",
            line, charPositionInLine, tokenText, traducirMensaje(msg)
        );

        errors.add(errorMsg);
    

    }
}