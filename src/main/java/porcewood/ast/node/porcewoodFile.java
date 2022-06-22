package porcewood.ast.node;

import java.util.*;
import porcewood.porcewoodParser.LetAssignmentContext;
import porcewood.porcewoodParser.LineContext;
import porcewood.porcewoodParser.StatementContext;
import porcewood.porcewoodParser.PorcewoodFileContext;

public class porcewoodFile implements Node {
  
  public List<Statement> statements;

  public porcewoodFile(List<Statement> statements){
    this.statements = statements;
  }

  public porcewoodFile() {
    this.statements = new ArrayList<Statement>();
  }
  
  public porcewoodFile(PorcewoodFileContext PorcewoodFileContext) {

    this.statements = new ArrayList<Statement>();

    for (int i = 0; i < PorcewoodFileContext.getChildCount(); i++) {
      LineContext line = PorcewoodFileContext.line(i);
      StatementContext statement = line.statement();
      LetAssignmentContext letassign = statement.letAssignment();
      statements.add(new LetAssignment(letassign));
    }

  }
  
  
}
