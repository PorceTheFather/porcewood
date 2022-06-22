package porcewood.ast;

import porcewood.ast.node.*;
import java.util.*;


public class ast {

  public List<Type> Types;

  public porcewoodFile file;

  public ast() {

    Type Int = new Type();
    this.Types = new ArrayList<Type>();
    Types.add(Int);

    this.file = new porcewoodFile();

    new validate(this);

    cleanupredund();

    rmintermidiatevars();


  }

  public ast(porcewoodFile pwf) {

    Type Int = new Type();
    this.Types = new ArrayList<Type>();
    Types.add(Int);

    this.file = pwf;

  }


  public void setporcewoodFile(porcewoodFile file) {
    this.file = file;
  }

  public void cleanupredund() {

    for (int i = 0; i < file.statements.size(); i++) {

      Statement stati = file.statements.get(i);
      if (stati.getClass() == LetAssignment.class) {
        LetAssignment leti = (LetAssignment) stati;
        if(leti.equal.left == leti.equal.right)
          file.statements.remove(i);
      }

    }

  }

  public void rmintermidiatevars() {

    for (int i = 0; i < file.statements.size(); i++) {

      Statement stati = file.statements.get(i);
      if (stati.getClass() == LetAssignment.class) {
        LetAssignment leti = (LetAssignment) stati;
        if(leti.equal.right.getClass() == Var.class) {
          recrmintermidiates(file.statements, 0, file.statements.listIterator());
        }
      }

    }

  }

  public List<Statement> recrmintermidiates(List<Statement> statements,
                                            int prevpos, ListIterator<Statement> listIter) {

    int pos = listIter.nextIndex() - 1;
    Statement stat = statements.get(pos);

    if(stat.getClass() != LetAssignment.class)
      throw new Error("somehow the recrmintermidiates function got called for something else than the LetAssignment");

    LetAssignment let = (LetAssignment) stat;

    if(let.equal.right.getClass() == IntLit.class) {
      Equal newequal = new Equal(((LetAssignment)statements.get(prevpos)).equal.left, let.equal.right);
      LetAssignment newlet = new LetAssignment(newequal);
      statements.set(prevpos, newlet);
      return statements;
    } else {

      for(;listIter.hasNext() && 
          ((Var)((LetAssignment)listIter.next()).equal.left).name 
          != ((Var)(let.equal.right)).name;);
      listIter.previous();

      recrmintermidiates(statements, pos, listIter);

      Equal newequal = new Equal(((LetAssignment)statements.get(prevpos)).equal.left, let.equal.right);
      LetAssignment newlet = new LetAssignment(newequal);
      statements.set(prevpos, newlet);

      return statements;
    
    }

  }


}
