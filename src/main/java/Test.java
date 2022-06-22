import porcewood.porcewoodParser;
import porcewood.porcewoodLexer;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;


public class Test {
  public static void main(String[] args) throws Exception {
    CharStream chstr = CharStreams.fromStream(System.in);
    porcewoodLexer lexer = new porcewoodLexer(chstr);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    porcewoodParser parser = new porcewoodParser(tokens);

    ParseTree file = parser.porcewoodFile();

    System.out.println(file.toStringTree(parser));
  }
  
}
