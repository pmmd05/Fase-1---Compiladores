package com.example.sintactico;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

public class SemanticListener extends gramaticaBaseListener {
    private final List<String> errors;
    private final Set<String> definidos = new HashSet<>();

    public SemanticListener(List<String> errors) {
        this.errors = errors;
    }

    /** 1) Al entrar en una asignación (rule assign): registra la variable como definida */
    @Override
    public void enterAssign(gramaticaParser.AssignContext ctx) {
        // ctx.ID() es el TerminalNode que contiene el nombre de la variable
        TerminalNode idNode = ctx.ID();
        if (idNode != null) {
            String id = idNode.getText();
            definidos.add(id);
        }
    }

    /** 2) Al entrar en cualquier referencia a variable (rule varReference): comprueba definición */
    @Override
    public void enterVarReference(gramaticaParser.VarReferenceContext ctx) {
        TerminalNode idNode = ctx.ID();
        if (idNode != null) {
            String id = idNode.getText();
            if (!definidos.contains(id)) {
                Token t = idNode.getSymbol();
                errors.add(String.format(
                    "Error semántico en línea %d, columna %d: variable '%s' no definida",
                    t.getLine(), t.getCharPositionInLine(), id
                ));
            }
        }
    }
}
