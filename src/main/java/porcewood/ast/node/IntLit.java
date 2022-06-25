package porcewood.ast.node;

import org.antlr.v4.runtime.tree.TerminalNode;

public class IntLit implements Expression {

  public String val;

  public IntLit(String val) {
    this.val = val;
  }

  public IntLit(TerminalNode termnode){
    this.val = termnode.getText();
  }

  public String toString() {
    return val;
  }
  
}
