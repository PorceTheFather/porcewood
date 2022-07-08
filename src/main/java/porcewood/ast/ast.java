package porcewood.ast;

import java.util.ArrayList;
import java.util.List;

import porcewood.porcewoodParser.ExpressionContext;
import porcewood.porcewoodParser.LetAssignmentContext;
import porcewood.porcewoodParser.PrintstatContext;

public class ast {

  public PorcewoodFileNode pwf;
  private List<VarLeaf> vars;

  public ast() {
    this.pwf = new PorcewoodFileNode();
    this.vars = new ArrayList<VarLeaf>();
  }
  
  public static interface astNode {
    public void setParent(astNode parent);
    public astNode getParent();
  }
  public static class astNodeImpl implements astNode {
  public astNode parent;

    public astNodeImpl(astNode parent) {
      setParent(parent);
    }
    
    public void setParent(astNode parent) {
      this.parent = parent;
    }
    public astNode getParent() {
      return this.parent;
    } 
  }
  public static class PorcewoodFileNode implements astNode {
    public static final astNode parent = null;
    public List<astNode> Statements;
    
    public PorcewoodFileNode() {
      this.Statements = new ArrayList<astNode>();
    }

    public void setParent(astNode parent) {}
    public astNode getParent() { return parent; }
  }
  public static class LetNode extends astNodeImpl{
    public VarLeaf var;
    public ExprNode expr;
    public astNode parent;
    public LetNode(astNode parent, String var, String expr) {
      super(parent);
      setVar(new VarLeaf(this, var));
      try {
        setExpr(new ExprNode(this, new IntLeaf(this, Integer.parseInt(expr))));
      } catch (Exception e) {
        setExpr(new ExprNode(this, new VarLeaf(this, expr)));
      }
    }
    public LetNode(astNode parent, LetAssignmentContext context) {
      super(parent);
      this.var = new VarLeaf(this, context.equal().VAR().getText());
      setExpr(new ExprNode(this, context.equal().expression()));
    }
    public void setVar(VarLeaf var) {
      this.var = var;
    }
    public void setExpr(ExprNode expr) {
      this.expr = expr;
    }
    public void setParent(astNode parent) {
      this.parent = parent;
    }
    public astNode getParent() {
      return this.parent;
    }
  }
  public static class PrintStatNode extends astNodeImpl {
    public ExprNode printexpr;

    public PrintStatNode(astNode parent, PrintstatContext printcon) {
      super(parent);
      this.printexpr = new ExprNode(this, printcon.expression());
    }

  }
  public static class ExprNode extends astNodeImpl {
    public astLeaf value;

    public ExprNode(astNode parent, astLeaf value) {
      super(parent);
      value.setParent(this);
    }
    public ExprNode(astNode parent, ExpressionContext expr) {
      super(parent);
      if ( expr.VAR() == null ) {
        this.value = new IntLeaf(this, Integer.parseInt(expr.INT().getText()));
      } else {
        this.value = new VarLeaf(this, expr.VAR().getText());
      }
    }

    public void setValue(astLeaf value) {
      this.value = value;
    }
  }
  public static interface astLeaf {
    public astNode getParent();
    public void setParent(astNode parent);
    public int getValue();
    public void setValue(int value);
  }
  public static class astLeafImpl implements astLeaf{
    public astNode parent;
    public int value;

    public astLeafImpl(astNode parent) {
      setParent(parent);
    }

    public void setValue(int value) {
      this.value = value;
    }
    public int getValue() {
      return this.value;
    }

    public void setParent(astNode parent) {
      this.parent = parent;
    }
    public astNode getParent() {
      return this.parent;
    }
  }
  public static class VarLeaf extends astLeafImpl {
    public String name;
    private int value;
    public boolean value_resolved;
    public VarLeaf(astNode parent, String name, int value) {
      super(parent);
      setName(name);
      setValue(value);
    }
    public VarLeaf(astNode parent, String name) {
      super(parent);
      setName(name);
    }
    public void setName(String name) {
      this.name = name;
    }
    public void setValue(int value) {
      this.value = value;
    }
    public int getValue() {
      return this.value;
    }
  }
  public static class IntLeaf extends astLeafImpl {
    public int value;
    public IntLeaf(astNode parent, int value) {
      super(parent);
      setValue(value);
    }
    public void setValue(int value) {
      this.value = value;
    }
    public int getValue() {
      return this.value;
    }
  }

  public void addLet(LetNode let) {
    this.pwf.Statements.add(let);
  }

  public void addPrint(PrintStatNode print) {
    this.pwf.Statements.add(print);
  }

  private void followlets() {
    resVars();
    for (astNode stat : pwf.Statements) {
      if (stat instanceof LetNode) {
        LetNode let = (LetNode)stat;
        System.out.println(let.expr.value);
        let.var.setValue(let.expr.value.getValue());
        System.out.println(let.var);
        let.var.value_resolved = true; 
      } 
    }
  }

  private void resVars() {
    for (astNode stat : pwf.Statements) {
      if (stat instanceof LetNode) {
        LetNode let = (LetNode)stat;
        for (VarLeaf varLeaf : vars) {
          if( varLeaf.name.equals(let.var.name) ) {
            let.setVar(varLeaf);
            break;
          }
        }
        vars.add(let.var);
      } else {
        PrintStatNode print = (PrintStatNode)stat;
        if(print.printexpr.value instanceof VarLeaf){
          for (VarLeaf varLeaf : vars) {
            VarLeaf var = (VarLeaf)print.printexpr.value;
            if (var.name.equals(varLeaf.name)) {
              print.printexpr.setValue(varLeaf);
              break;
            }
          }
          vars.add((VarLeaf)print.printexpr.value);
        }
      }
    }

  }

  private void reNameDoubleDecaration() {
    List<String> vars = new ArrayList<String>();
    for (astNode stat : pwf.Statements) {
      if (stat instanceof LetNode) {
        vars.add(((LetNode)stat).var.name);
      }
    }
    for (String name : vars) {
      while (vars.indexOf(name) != vars.lastIndexOf(name)) {
        vars.set(vars.lastIndexOf(name), String.valueOf(name.hashCode()));
      }
    }
    int i = 0,a = 0;
    for (astNode stat : pwf.Statements) {
      if (stat instanceof LetNode) {
        ((LetNode)pwf.Statements.get(i)).var.name = vars.get(a);
        a++;
      }
      i++;
    }
  }

  public void cleanup() {
    //reNameDoubleDecaration();
    followlets();
  }
}
