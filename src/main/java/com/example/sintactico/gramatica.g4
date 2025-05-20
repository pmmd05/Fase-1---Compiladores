grammar gramatica;

@header {
    package com.example.sintactico;
}

// ---------------------
// Parser rules
// ---------------------

prog
    : statement* EOF
    ;

statement
    : assignment ';'?        # StmtAssign
    | expression ';'?        # StmtExpr
    ;

assignment
    : ID ASSIGN expression   # Assign
    ;

// Expresiones con precedencia y asociatividad
expression
    : <assoc=right> expression POW expression    # Pow
    | expression (MUL | DIV) expression          # MulDiv
    | expression (PLUS|MINUS) expression         # AddSub
    | LPAREN expression RPAREN                   # Parens
    | MINUS expression                           # UnaryMinus
    | NUMBER                                     # NumberLiteral
    | ID                                         # VarReference
    ;

// ---------------------
// Lexer rules
// ---------------------

// Identificador de variable
ID      : [a-zA-Z] [a-zA-Z0-9]* ;

// Números enteros y decimales
NUMBER  : [0-9]+ ('.' [0-9]+)? ;

// Asignación
ASSIGN  : '==' ;

// Operadores
PLUS    : '+' ;
MINUS   : '-' ;
MUL     : '*' ;
DIV     : '/' ;
POW     : '^' ;

// Agrupadores
LPAREN  : '(' ;
RPAREN  : ')' ;

// Espacios y nuevas líneas
WS      : [ \t\r\n]+ -> skip ;
