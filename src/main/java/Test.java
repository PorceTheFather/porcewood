import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import porcewood.porcewoodLexer;
import porcewood.porcewoodParser;

public class Test {

  public static void main(String[] args) throws Exception {
    ANTLRInputStream input = new ANTLRInputStream(System.in);
    porcewoodLexer lexer = new porcewoodLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    porcewoodParser parser = new porcewoodParser(tokens);
    ParseTree tree = parser.porcewoodFile();
    System.out.println(tree.toStringTree(parser));
  }
  
}
