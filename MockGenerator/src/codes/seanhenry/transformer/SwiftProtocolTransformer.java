package codes.seanhenry.transformer;

import codes.seanhenry.mockgenerator.entities.Initialiser;
import codes.seanhenry.mockgenerator.entities.Parameter;
import codes.seanhenry.util.MySwiftPsiUtil;
import codes.seanhenry.util.finder.SwiftTypeItemFinder;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class SwiftProtocolTransformer extends SwiftTypeTransformer {

  public SwiftProtocolTransformer(SwiftTypeItemFinder itemFinder) {
    super(itemFinder);
  }

  @Nullable
  @Override
  protected Initialiser transformInitialiser(SwiftInitializerDeclaration initialiser) {
    return null;
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

  @Override
  protected String getReturnType(SwiftFunctionDeclaration method) {
    return MySwiftPsiUtil.getReturnTypeName(method);
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
    return method.getText();
  }
}
