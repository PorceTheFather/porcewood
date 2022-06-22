package porcewood.parser;

import porcewood.porcewoodLexer;
import porcewood.porcewoodParser;
import porcewood.porcewoodParser.*;
import porcewood.ast.*;
import porcewood.ast.node.*;

import org.antlr.v4.runtime.*;


public class parser {

  public ast ast;

  public parser(CharStream code) {
    
    porcewoodLexer lexer = new porcewoodLexer(code);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    porcewoodParser parser = new porcewoodParser(tokens);
    PorcewoodFileContext pwfc = parser.porcewoodFile();
    porcewoodFile pwf = new porcewoodFile(pwfc);
    
    this.ast = new ast(pwf);
    
  }

  public ast getAst() {
    return ast;
  }
  
}
