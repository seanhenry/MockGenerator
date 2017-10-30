package codes.seanhenry.transformer;

import codes.seanhenry.mockgenerator.entities.Parameter;
import codes.seanhenry.util.MySwiftPsiUtil;
import codes.seanhenry.util.finder.SwiftTypeItemFinder;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class SwiftClassTransformer extends SwiftTypeTransformer {

  public SwiftClassTransformer(SwiftTypeItemFinder itemFinder) {
    super(itemFinder);
  }

  @Override
  protected String getName(SwiftVariableDeclaration property) {
    return MySwiftPsiUtil.getName(property);
  }

  @Override
  protected String getType(SwiftVariableDeclaration property) {
    String explicitType = MySwiftPsiUtil.getExplicitType(property);
    if (explicitType != null) {
      return explicitType;
    }
    return MySwiftPsiUtil.getInferredType(property);
  }

  @Override
  protected boolean isWritable(SwiftVariableDeclaration property) {
    return !property.isConstant() && !MySwiftPsiUtil.isComputed(property);
  }

  @NotNull
  @Override
  protected String getSignature(SwiftVariableDeclaration property) {
    SwiftInitializer initializer = PsiTreeUtil.findChildOfType(property, SwiftInitializer.class);
    if (initializer == null) {
      return property.getText();
    }
    String lhs = property.getContainingFile().getText().substring(property.getTextOffset(), initializer.getTextOffset());
    return lhs + ": " + getType(property);
  }

  @Override
  protected String getName(SwiftFunctionDeclaration method) {
    return method.getName();
  }

  @Override
  protected String getReturnType(SwiftFunctionDeclaration method) {
    return MySwiftPsiUtil.getReturnType(method);
  }

  @Override
  protected List<Parameter> getParameters(SwiftFunctionDeclaration method) {
    return MySwiftPsiUtil.getParameters(method)
        .stream()
        .map(this::transformParameter)
        .collect(Collectors.toList());
  }

  @NotNull
  @Override
  protected String getSignature(SwiftFunctionDeclaration method) {
    SwiftCodeBlock codeBlock = method.getCodeBlock();
    if (codeBlock != null) {
      int offset = method.getStartOffsetInParent();
      int length = codeBlock.getTextOffset();
      return method.getContainingFile().getText().substring(offset, length);
    }
    return method.getText();
  }
}
