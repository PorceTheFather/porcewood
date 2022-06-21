lexer grammar porcewoodLexer;


LET : 'let';
EQ : '=';

INT : '0'|[1-9][0-9]* ;
VAR : [_]*[a-z][A-Za-z0-9_]* ;

WS : [\t ]+ -> skip;
NEWLINE : '\r\n' | '\r' | '\n' ;
