package codes.seanhenry.transformer;

import codes.seanhenry.util.MySwiftPsiUtil;
import codes.seanhenry.util.finder.TypeItemFinder;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class SwiftProtocolTransformer extends SwiftTypeTransformer {

  public SwiftProtocolTransformer(TypeItemFinder itemFinder) {
    super(itemFinder);
  }

  @Override
  protected boolean isProtocol() {
    return true;
  }

  @Override
  protected String getName(SwiftVariableDeclaration property) {
    return MySwiftPsiUtil.getName(property);
  }

  @Override
  protected String getType(SwiftVariableDeclaration property) {
    return MySwiftPsiUtil.getExplicitTypeName(property);
  }

  @Override
  protected boolean isWritable(SwiftVariableDeclaration property) {
    return PsiTreeUtil.findChildOfType(property, SwiftSetterClause.class) != null;
  }

  @NotNull
  @Override
  protected String getSignature(SwiftVariableDeclaration property) {
    return property.getText();
  }

  @Override
  protected String getName(SwiftFunctionDeclaration method) {
    return method.getName();
  }

  @NotNull
  @Override
  protected String getSignature(SwiftFunctionDeclaration method) {
    return method.getText();
  }
}
