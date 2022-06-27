package porcewood.visitors.optimize;


import porcewood.porcewoodBaseVisitor;
import static porcewood.porcewoodParser.*;

import java.util.List;
import java.util.ArrayList;

import org.antlr.v4.runtime.tree.TerminalNode;

public class validate extends porcewoodBaseVisitor<Void> {

  private List<String> declared_vars; 

  public validate() {
    this.declared_vars = new ArrayList<String>();
  }

  @Override
  public Void visitLetAssignment(LetAssignmentContext context) {
    TerminalNode var = context.equal().VAR();
    declared_vars.add(var.getText());
    ExpressionContext expr = context.equal().expression();
    if (expr.VAR() == null)
      return visitChildren(context);

    if (!declared_vars.contains(expr.VAR().getText())) 
      throw new Error("varibale " + expr.VAR() + " not declared");
    
    return visitChildren(context);
  }

  @Override
  public Void visitPrintstat(PrintstatContext context) {

    if (context.expression().VAR() == null)
      return visitChildren(context);
    else if (!declared_vars.contains(context.expression().VAR().getText())) 
      throw new Error("varibale " + context.expression().VAR() + " not declared");
    
    return visitChildren(context);
  }
  
}
