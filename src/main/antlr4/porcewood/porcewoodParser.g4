parser grammar porcewoodParser;

options { tokenVocab=porcewoodLexer; }

porcewoodFile : lines=line+ ;

line          : statement (NEWLINE | EOF) ;

statement     : letAssignment ;

letAssignment : LET equal ;

equal         : VAR EQ (VAR | INT) ;
