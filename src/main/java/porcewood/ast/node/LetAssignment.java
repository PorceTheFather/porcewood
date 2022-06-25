package porcewood.ast.node;

import porcewood.porcewoodParser.LetAssignmentContext;

public class LetAssignment implements Statement {

  public Equal equal;

  public LetAssignment(Equal equal) {
    this.equal = equal;
  }

  public LetAssignment(LetAssignmentContext letasscont) {
    this.equal = new Equal(letasscont.equal());
  }

  public String toString() {
    return "let " + equal.toString();
  }
  
}
