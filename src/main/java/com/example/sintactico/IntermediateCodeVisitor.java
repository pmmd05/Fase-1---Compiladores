package com.example.sintactico;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IntermediateCodeVisitor extends gramaticaBaseVisitor<String> {
    private final List<String> instructions = new ArrayList<>();
    // Para generar temporales t1, t2, ...
    private final AtomicInteger tempCounter = new AtomicInteger(1);

    /** Devuelve el nombre de un nuevo temporal */
    private String newTemp() {
        return "t" + tempCounter.getAndIncrement();
    }

    /** Al terminar, tus instrucciones intermedias: */
    public List<String> getInstructions() {
        return instructions;
    }

    // VISITORS

    @Override
    public String visitAssign(gramaticaParser.AssignContext ctx) {
        String id   = ctx.ID().getText();
        // Genera código para la expresión de la derecha
        String rhsTemp = visit(ctx.expression());
        // Emite: id = rhsTemp
        instructions.add(id + " = " + rhsTemp);
        return id;
    }

    @Override
    public String visitStmtExpr(gramaticaParser.StmtExprContext ctx) {
        // Igual que Assign, pero sin ID: sólo evaluamos y emitimos un PRINT, por ejemplo
        String t = visit(ctx.expression());
        instructions.add("print " + t);
        return t;
    }

    @Override
    public String visitAddSub(gramaticaParser.AddSubContext ctx) {
        // Visita hijos, obtén sus temporales o literales
        String left  = visit(ctx.expression(0));
        String right = visit(ctx.expression(1));
        String temp  = newTemp();
        String op    = ctx.PLUS() != null ? "+" : "-";
        instructions.add(temp + " = " + left + " " + op + " " + right);
        return temp;
    }

    @Override
    public String visitMulDiv(gramaticaParser.MulDivContext ctx) {
        String left  = visit(ctx.expression(0));
        String right = visit(ctx.expression(1));
        String temp  = newTemp();
        String op    = ctx.MUL() != null ? "*" : "/";
        instructions.add(temp + " = " + left + " " + op + " " + right);
        return temp;
    }

    @Override
    public String visitPow(gramaticaParser.PowContext ctx) {
        String base = visit(ctx.expression(0));
        String exp  = visit(ctx.expression(1));
        String temp = newTemp();
        instructions.add(temp + " = pow " + base + ", " + exp);
        return temp;
    }

    @Override
    public String visitUnaryMinus(gramaticaParser.UnaryMinusContext ctx) {
        String val  = visit(ctx.expression());
        String temp = newTemp();
        instructions.add(temp + " = - " + val);
        return temp;
    }

    @Override
    public String visitParens(gramaticaParser.ParensContext ctx) {
        // Simplemente delega
        return visit(ctx.expression());
    }

    @Override
    public String visitNumberLiteral(gramaticaParser.NumberLiteralContext ctx) {
        // Literales devuelven su propio texto
        return ctx.NUMBER().getText();
    }

    @Override
    public String visitVarReference(gramaticaParser.VarReferenceContext ctx) {
        return ctx.ID().getText();
    }
}
