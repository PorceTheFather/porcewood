package porcewood.ast.node;

import org.antlr.v4.runtime.tree.TerminalNode;

import porcewood.porcewoodParser.EqualContext;
import porcewood.porcewoodParser.ExpressionContext;

public class Equal implements Statement {

  public Expression left;

  public Expression right;

  public Equal(Expression left, Expression right) {
    this.left = left;
    this.right = right;
  }

  public Equal(EqualContext eqcont) {
    this.left = new Var(eqcont.VAR());
    this.right = toExpr(eqcont.expression());
  }

  private Expression toExpr(ExpressionContext exprcont) {
    TerminalNode expint = exprcont.INT();
    TerminalNode expvar = exprcont.VAR();
    if (expint != null) {
      return new IntLit(expint);
    } else if(expvar != null){
      return new Var(expvar);
    } else {
      throw new Error("weird let assignment ");
    }
    
  }

  public String toString() {
    return left.toString() + " = " + right.toString();
  }
  
}
