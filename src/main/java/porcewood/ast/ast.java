package porcewood.ast;

import porcewood.ast.node.*;
import java.util.*;


public class ast {

  public List<Type> Types;

  public porcewoodFile file;


  public ast(porcewoodFile pwf) {

    Type Int = new Type();
    this.Types = new ArrayList<Type>();
    Types.add(Int);

    this.file = pwf;

    new validate(this);

  }


  public void setporcewoodFile(porcewoodFile file) {
    this.file = file;
  }


}
