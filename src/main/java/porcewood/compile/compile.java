package porcewood.compile;

import porcewood.ast.ast;
import static porcewood.ast.ast.*;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;



public class compile {
  public ast ast;
  public String ccode;
  public String progname;
  public File ccodeFile;

  public compile(ast ast, Path file) throws Exception{
    this.ast = ast;
    this.ast.cleanup();
    this.progname = file.toFile().getName();
    this.progname = progname.substring(0, progname.lastIndexOf(".porcewood"));
    compiletoNative();
  }

  private void genPrints() {
    for (astNode var : ast.pwf.Statements) {
      if (var instanceof PrintStatNode) {
        ccode += "\tprintf(\"%d\", " + ((PrintStatNode)var).printexpr.value.getValue() + ");\n";
      }
    }
  }

  private void genHeaders() {
    ccode = "#include <stdio.h>\n";
  } 

  private void beginMain() {
    ccode += "\nint main() {\n";
  }

  private void endMain() {
    ccode += "}";
  }

  private void genMain() {
    genHeaders();
    beginMain();
    genPrints();
    endMain();
  }

  private void genCCodefile() throws Exception{
    genMain();
    FileWriter fwr = new FileWriter(progname + ".c");
    fwr.write(ccode);
    fwr.flush();
    fwr.close();
    ccodeFile = new File(progname + ".c");
  }

  // https://www.geeksforgeeks.org/calling-external-program-java-using-process-runtime/

  public void compiletoNative() throws Exception{
    genCCodefile();
    String [] command = new String[2];
    command[0] = "gcc";
    command[1] = ccodeFile.getName();
    Runtime run = Runtime.getRuntime();
    run.exec(command);
  }
}
