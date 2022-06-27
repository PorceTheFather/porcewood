package porcewood.visitors.compile;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Pointer;
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

import porcewood.porcewoodParser;
import porcewood.porcewoodParser.LetAssignmentContext;
import porcewood.porcewoodParser.PorcewoodFileContext;
import porcewood.porcewoodBaseVisitor;

public class compile extends porcewoodBaseVisitor<Void> {
  
  public static final BytePointer error = new BytePointer();

  private final String filepath;
  private final String progname;

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
    LLVMBuilderRef builder = LLVMCreateBuilderInContext(context);
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

    return null;
  }
}
