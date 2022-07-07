package porcewood;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.*;

import porcewood.ast.ast;
import porcewood.compile.compile;
import porcewood.porcewoodParser.PorcewoodFileContext;
import porcewood.visitors.ast.GenAst;
import porcewood.visitors.optimize.rmunneeded;
import porcewood.visitors.optimize.validate;

public class Main {
  public static void main(String[] args) throws Exception {
    Path a = Paths.get(args[0]);
    CharStream b = CharStreams.fromPath(a);
    porcewoodLexer lexer = new porcewoodLexer(b);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    porcewoodParser parser = new porcewoodParser(tokens);

    PorcewoodFileContext pwf = parser.porcewoodFile();
    pwf.accept(new rmunneeded());
    pwf.accept(new validate());
    System.out.println(pwf.toStringTree(parser));
    ast ast = GenAst.AstFromPorcewoodFileContext(pwf);
    ast.cleanup();
    new compile(ast, a);
  }
}
