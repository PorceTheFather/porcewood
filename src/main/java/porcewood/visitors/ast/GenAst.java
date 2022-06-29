package porcewood.visitors.ast;

import porcewood.porcewoodBaseVisitor;
import porcewood.ast.ast;
import porcewood.porcewoodParser.LetAssignmentContext;
import porcewood.porcewoodParser.PorcewoodFileContext;
import porcewood.porcewoodParser.PrintstatContext;

import static porcewood.ast.ast.*;

public class GenAst extends porcewoodBaseVisitor<Void> {
  public ast ast;

  public GenAst() {
    this.ast = new ast();
  }

  @Override
  public Void visitLetAssignment(LetAssignmentContext context){
    ast.addLet(new LetNode(ast.pwf, context));
    return visitChildren(context);
  }
  
  @Override
  public Void visitPrintstat(PrintstatContext context) {
    ast.addPrint(new PrintStatNode(ast.pwf, context));
    return visitChildren(context);
  }

  public static final ast AstFromPorcewoodFileContext(PorcewoodFileContext context) {
    GenAst temp = new GenAst();
    context.accept(temp);
    return temp.ast;
  }
}
