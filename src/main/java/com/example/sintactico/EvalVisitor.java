package com.example.sintactico;

import java.util.HashMap;
import java.util.Map;

import com.example.sintactico.gramaticaParser.AddSubContext;
import com.example.sintactico.gramaticaParser.AssignContext;
import com.example.sintactico.gramaticaParser.MulDivContext;
import com.example.sintactico.gramaticaParser.NumberLiteralContext;
import com.example.sintactico.gramaticaParser.ParensContext;
import com.example.sintactico.gramaticaParser.PowContext;
import com.example.sintactico.gramaticaParser.StmtExprContext;
import com.example.sintactico.gramaticaParser.UnaryMinusContext;
import com.example.sintactico.gramaticaParser.VarReferenceContext;

public class EvalVisitor extends gramaticaBaseVisitor<Double> {
    private final Map<String, Double> memory = new HashMap<>();

    @Override
    public Double visitAssign(gramaticaParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        double value = visit(ctx.expression());
        memory.put(id, value);
        System.out.printf("%s = %s%n", id, value);
        return value;
    }

    @Override
    public Double visitStmtExpr(gramaticaParser.StmtExprContext ctx) {
        double val = visit(ctx.expression());
        System.out.println(val);
        return val;
    }

    @Override
    public Double visitAddSub(gramaticaParser.AddSubContext ctx) {
        double left  = visit(ctx.expression(0));
        double right = visit(ctx.expression(1));
        return ctx.PLUS()  != null ? left + right
             : /*MINUS*/      left - right;
    }

    @Override
    public Double visitMulDiv(gramaticaParser.MulDivContext ctx) {
        double left  = visit(ctx.expression(0));
        double right = visit(ctx.expression(1));
        return ctx.MUL() != null ? left * right
             : /*DIV*/       left / right;
    }

    @Override
    public Double visitPow(gramaticaParser.PowContext ctx) {
        double base = visit(ctx.expression(0));
        double exp  = visit(ctx.expression(1));
        return Math.pow(base, exp);
    }

    @Override
    public Double visitParens(gramaticaParser.ParensContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Double visitUnaryMinus(gramaticaParser.UnaryMinusContext ctx) {
        return -visit(ctx.expression());
    }

    @Override
    public Double visitNumberLiteral(gramaticaParser.NumberLiteralContext ctx) {
        return Double.parseDouble(ctx.NUMBER().getText());
    }

    @Override
    public Double visitVarReference(gramaticaParser.VarReferenceContext ctx) {
        String id = ctx.ID().getText();
        if (!memory.containsKey(id)) {
            throw new RuntimeException("Variable no definida: " + id);
        }
        return memory.get(id);
    }

    public Map<String,Double> getMemory() {
        return memory;
    }
}
