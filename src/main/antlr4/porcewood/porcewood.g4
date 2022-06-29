grammar porcewood;


porcewoodFile : lines=line+ ;

line          : statement (NEWLINE | EOF) ;

statement     : letAssignment | printstat ;

letAssignment : LET equal ;

printstat : PRINT '(' expression ')' ;

equal         : VAR EQ expression ;

expression    : VAR | INT ;

LET : 'let';
EQ : '=';
PRINT : 'out';

INT : '0'|[1-9][0-9]*|'-'[1-9][0-9]* ;
VAR : [_]*[a-z][A-Za-z0-9_]* ;

WS : [\t ]+ -> skip;
NEWLINE : '\r\n' | '\r' | '\n' ;
