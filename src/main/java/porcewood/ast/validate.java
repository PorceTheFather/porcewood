package porcewood.ast;

import porcewood.ast.node.*;
import porcewood.ast.node.LetAssignment;

import java.util.List;

public class validate {

  private List<Statement> statements;

  
  public validate(ast ast) {


    this.statements = ast.file.statements;

    verifydeclaration(ast);

    
  }

  private void verifydeclaration(ast ast) {


    for (int i = 0; i < statements.size(); i++) {
      Statement stat = statements.get(i);
      if(stat.getClass() == LetAssignment.class) {

        LetAssignment let = (LetAssignment) stat;
        if(let.equal.right.getClass() == Var.class) {
          if(searchfirst(((Var)let.equal.right)) == -1) {

            throw new Error("var not declared");

          }
        }
      
      }
    }
    
  }

  private final int searchfirst(Var var) {

    for (int i = 0; i < statements.size(); i++) {
      LetAssignment let = (LetAssignment)statements.get(i);
      if(var == let.equal.left){
        return i;
      }
    }
    return -1;

  }



}
