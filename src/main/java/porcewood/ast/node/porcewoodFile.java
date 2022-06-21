package porcewood.ast.node;

import java.beans.Statement;
import java.util.*;

public class porcewoodFile implements Node {
  
  public List<Statement> statements;

  public porcewoodFile(List<Statement> statements){
    this.statements = statements;
  }
  
}
