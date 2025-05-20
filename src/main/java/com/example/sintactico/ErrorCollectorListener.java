package com.example.sintactico;

import org.antlr.v4.runtime.*;
import java.util.List;

public class ErrorCollectorListener extends BaseErrorListener {
    private final List<String> errors;
    public ErrorCollectorListener(List<String> errors) {
        this.errors = errors;
    }
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg,
                            RecognitionException e) {
        errors.add(String.format("Línea %d Col %d: %s", line, charPositionInLine, msg));
    }
}