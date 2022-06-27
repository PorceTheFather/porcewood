import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.*;

import porcewood.porcewoodLexer;
import porcewood.porcewoodParser;
import porcewood.porcewoodParser.PorcewoodFileContext;
import porcewood.visitors.optimize.rmunneeded;
import porcewood.visitors.optimize.validate;

public class Test {
  public static void main(String[] args) throws Exception {
    Path a = Paths.get("../../test.porcewood");
    CharStream b = CharStreams.fromPath(a);
    porcewoodLexer lexer = new porcewoodLexer(b);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    porcewoodParser parser = new porcewoodParser(tokens);

    PorcewoodFileContext pwf = parser.porcewoodFile();
    pwf.accept(new rmunneeded());
    pwf.accept(new validate());
    System.out.println(pwf.toStringTree(parser));
  }
}
