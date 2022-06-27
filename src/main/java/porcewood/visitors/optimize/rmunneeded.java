package porcewood.visitors.optimize;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;

import java.util.ArrayList;
import java.util.List;

import porcewood.porcewoodBaseVisitor;
import porcewood.porcewoodParser.EqualContext;
import porcewood.porcewoodParser.LetAssignmentContext;
import porcewood.porcewoodParser.LineContext;
import porcewood.porcewoodParser.PorcewoodFileContext;
import porcewood.porcewoodParser.PrintstatContext;

public class rmunneeded extends porcewoodBaseVisitor<Void> {

  public List<Boolean> removevars;

  @Override
  public Void visitLetAssignment(LetAssignmentContext context) {
    EqualContext equal = context.equal();
    if(equal.VAR().getText().equals(equal.expression().getText())) {
      removevars.add(true);
    } else {
      removevars.add(false);
    }
    return null;
  }

  @Override
  public Void visitPrintstat(PrintstatContext context) {
    removevars.add(false);
    return visitChildren(context);
  }

  @Override
  public Void visitPorcewoodFile(PorcewoodFileContext context) {
    this.removevars = new ArrayList<Boolean>();
    visitChildren(context);
    int ind = 0;
    for (boolean i  : removevars) {
      if(i) {
        context.children.remove(ind);
      } 
      ind++;
    }
    return null;
  }
  
}
