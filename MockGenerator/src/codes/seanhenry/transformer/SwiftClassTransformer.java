package codes.seanhenry.transformer;

import codes.seanhenry.mockgenerator.entities.Initialiser;
import codes.seanhenry.mockgenerator.entities.Parameter;
import codes.seanhenry.util.MySwiftPsiUtil;
import codes.seanhenry.util.finder.SwiftTypeItemFinder;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.swift.psi.*;
import com.jetbrains.swift.symbols.SwiftDeclarationSpecifiers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class SwiftClassTransformer extends SwiftTypeTransformer {

  public SwiftClassTransformer(SwiftTypeItemFinder itemFinder) {
    super(itemFinder);
  }

  @Override
  protected boolean isProtocol() {
    return false;
  }

  @Override
  protected String getName(SwiftVariableDeclaration property) {
    return MySwiftPsiUtil.getName(property);
  }

  @Override
  protected String getType(SwiftVariableDeclaration property) {
    return MySwiftPsiUtil.getTypeName(property);
  }

  @Override
  protected boolean isWritable(SwiftVariableDeclaration property) {
    return !property.isConstant() && !MySwiftPsiUtil.isComputed(property) && !MySwiftPsiUtil.isPrivateSet(property) && !MySwiftPsiUtil.isFilePrivateSet(property);
  }

  @NotNull
  @Override
  protected String getSignature(SwiftVariableDeclaration property) {
    int startOffset = getStartOffset(property);
    int endOffset = getEndOffset(property);
    String signature = property.getContainingFile().getText().substring(startOffset, endOffset);
    if (MySwiftPsiUtil.hasExplicitType(property)) {
      return signature;
    }
    return signature + ": " + getType(property);
  }

  private int getStartOffset(SwiftVariableDeclaration property) {
    return property.getTextOffset() + property.getAttributes().getTextLength();
  }

  private int getEndOffset(SwiftVariableDeclaration property) {
    SwiftInitializer initializer = PsiTreeUtil.findChildOfType(property, SwiftInitializer.class);
    int endOffset = property.getTextOffset() + property.getTextLength();
    if (initializer != null) {
      endOffset = initializer.getTextOffset();
    }
    return endOffset;
  }

  @Override
  protected String getName(SwiftFunctionDeclaration method) {
    return method.getName();
  }

  @Override
  protected String getReturnType(SwiftFunctionDeclaration method) {
    return MySwiftPsiUtil.getReturnTypeName(method);
  }

  @NotNull
  @Override
  protected String getSignature(SwiftFunctionDeclaration method) {
    SwiftCodeBlock codeBlock = method.getCodeBlock();
    int methodOffset = method.getStartOffsetInParent();
    int startOffset = methodOffset + method.getAttributes().getStartOffsetInParent() + method.getAttributes().getTextLength();
    int endOffset = methodOffset + method.getTextLength();
    if (codeBlock != null) {
      endOffset = methodOffset + codeBlock.getStartOffsetInParent();
    }
    return method.getContainingClass().getText().substring(startOffset, endOffset);
  }
}
