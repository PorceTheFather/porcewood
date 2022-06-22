package porcewood.ast.node;

import org.antlr.v4.runtime.tree.TerminalNode;

public class Var implements Expression {
  
  public String name;

  public Var(String name) {
    this.name = name;
  }

  public Var(TerminalNode termnode) {
    this.name = termnode.getText();
  }

}
