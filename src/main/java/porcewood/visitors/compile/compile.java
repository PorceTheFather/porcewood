package porcewood.visitors.compile;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.llvm.LLVM.LLVMBasicBlockRef;
import org.bytedeco.llvm.LLVM.LLVMBuilderRef;
import org.bytedeco.llvm.LLVM.LLVMContextRef;
import org.bytedeco.llvm.LLVM.LLVMExecutionEngineRef;
import org.bytedeco.llvm.LLVM.LLVMGenericValueRef;
import org.bytedeco.llvm.LLVM.LLVMMemoryBufferRef;
import org.bytedeco.llvm.LLVM.LLVMModuleRef;
import org.bytedeco.llvm.LLVM.LLVMTargetMachineRef;
import org.bytedeco.llvm.LLVM.LLVMTargetRef;
import org.bytedeco.llvm.LLVM.LLVMTypeRef;
import org.bytedeco.llvm.LLVM.LLVMValueRef;

import static org.bytedeco.llvm.global.LLVM.*;

import java.util.List;

import porcewood.porcewoodParser;
import porcewood.porcewoodParser.EqualContext;
import porcewood.porcewoodParser.ExpressionContext;
import porcewood.porcewoodParser.LetAssignmentContext;
import porcewood.porcewoodParser.PorcewoodFileContext;
import porcewood.porcewoodParser.PrintstatContext;
import porcewood.porcewoodBaseVisitor;

public class compile extends porcewoodBaseVisitor<Void> {
  
  public static final BytePointer error = new BytePointer();

  private final String filepath;
  private final String progname;
  private LLVMBuilderRef builder;
  private LLVMTypeRef i64Type;
  private List<LLVMValueRef> vars;

  public compile(String filepath) {
    // Initialize LLVM components
    LLVMInitializeCore(LLVMGetGlobalPassRegistry());
    LLVMInitializeNativeAsmPrinter();
    LLVMInitializeNativeAsmParser();
    LLVMInitializeNativeDisassembler();
    LLVMInitializeNativeTarget();

    this.filepath = filepath;
    this.progname = filepath.replaceAll("*/", "");
  }

  @Override
  public Void visitPorcewoodFile(PorcewoodFileContext pwfcontext) {
    LLVMContextRef context = LLVMContextCreate();
    LLVMModuleRef module = LLVMModuleCreateWithNameInContext(progname, context);
    builder = LLVMCreateBuilderInContext(context);
    LLVMTypeRef i64Type = LLVMInt64TypeInContext(context);
    LLVMTypeRef mainType = LLVMFunctionType(i64Type, i64Type, 0, 0);

    LLVMValueRef main = LLVMAddFunction(module, "main", mainType);
    LLVMSetFunctionCallConv(main, LLVMCCallConv);
    

    LLVMBasicBlockRef entry = LLVMAppendBasicBlockInContext(context, main, "entry");

    LLVMPositionBuilderAtEnd(builder, entry);

    super.visitPorcewoodFile(pwfcontext);

    

    return null;
  }

  @Override
  public Void visitLetAssignment(LetAssignmentContext context) {
    EqualContext equal = context.equal();
    if (equal.expression().VAR() == null) {
      LLVMValueRef var = LLVMBuildAlloca(builder, i64Type, equal.VAR().getText());
      LLVMValueRef value = LLVMConstInt(i64Type, Integer.parseInt(equal.expression().INT().getText()), 1);
      LLVMBuildStore(builder, value, var);
      
      vars.add(var);
    } else {

      throw new Error("not implemented");
      
    }

    return visitChildren(context);
  }

  @Override
  public Void visitPrintstat(PrintstatContext context) {
    ExpressionContext expr = context.expression();
  

    return visitChildren(context);
  }
}
