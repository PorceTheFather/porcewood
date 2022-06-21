package porcewood.ast.node;

public class Int implements Type {

  public int value;

  public String repr;

  public Int(int value) {
    this.value = value;
    this.repr = Integer.toString(value);
  }


  public Int(String repr) {
    this.value = Integer.parseInt(repr);
    this.repr = repr;
  }
  
}
