// Generated from gramatica.g4 by ANTLR 4.13.2

    package com.example.sintactico;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link gramaticaParser}.
 */
public interface gramaticaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link gramaticaParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(gramaticaParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramaticaParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(gramaticaParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StmtAssign}
	 * labeled alternative in {@link gramaticaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStmtAssign(gramaticaParser.StmtAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StmtAssign}
	 * labeled alternative in {@link gramaticaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStmtAssign(gramaticaParser.StmtAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StmtExpr}
	 * labeled alternative in {@link gramaticaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStmtExpr(gramaticaParser.StmtExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StmtExpr}
	 * labeled alternative in {@link gramaticaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStmtExpr(gramaticaParser.StmtExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link gramaticaParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssign(gramaticaParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link gramaticaParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssign(gramaticaParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarReference}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVarReference(gramaticaParser.VarReferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarReference}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVarReference(gramaticaParser.VarReferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(gramaticaParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(gramaticaParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(gramaticaParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(gramaticaParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParens(gramaticaParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParens(gramaticaParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Pow}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPow(gramaticaParser.PowContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Pow}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPow(gramaticaParser.PowContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryMinus}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinus(gramaticaParser.UnaryMinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryMinus}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinus(gramaticaParser.UnaryMinusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNumberLiteral(gramaticaParser.NumberLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link gramaticaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNumberLiteral(gramaticaParser.NumberLiteralContext ctx);
}