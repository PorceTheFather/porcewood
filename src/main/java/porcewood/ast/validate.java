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
          if(!declaredbefore(((Var)let.equal.right))) {

            throw new Error("var not declared: " + ((Var)let.equal.right).name);

          }
        }
      
      }
    }
    
  }

  private boolean declaredbefore(Var var) {

    System.out.println("Var name:" + var.name);

    for (int i = 0; i < statements.size(); i++) {
      LetAssignment let = (LetAssignment)statements.get(i);
      if(var.toString().equals(let.equal.left.toString())) {
        return true;
      }
    }

    return false;

  }

}
