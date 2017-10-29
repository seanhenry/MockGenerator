package codes.seanhenry.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.swift.psi.*;
import com.jetbrains.swift.symbols.SwiftDeclarationSpecifiers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static java.util.Collections.emptyList;

public class MySwiftPsiUtil {

  public static boolean isFinal(SwiftAttributesHolder attributesHolder) {
    return attributesHolder.getAttributes().hasDeclarationSpecifier(SwiftDeclarationSpecifiers.FINAL);
  }

  @Nullable
  public static String getName(SwiftVariableDeclaration property) {
     List<SwiftIdentifierPattern> variables = property.getVariables();
     if (variables.isEmpty()) {
       return null;
     }
     return variables.get(0).getText();
  }

  @Nullable
  public static String getExplicitType(SwiftVariableDeclaration property) {
    SwiftTypeElement type = PsiTreeUtil.findChildOfType(property, SwiftTypeElement.class);
    if (type != null) {
      return type.getText();
    }
    return null;
  }

  @Nullable
  public static String getInferredType(SwiftVariableDeclaration property) {
    SwiftInitializer initializer = PsiTreeUtil.findChildOfType(property, SwiftInitializer.class);
    if (initializer == null) {
      return null;
    }
    String result = getExpressInferredType(initializer);
    if (result != null) {
      return result;
    }
    result = getLiteralInferredType(initializer);
    if (result != null) {
      return result;
    }
    return null;
  }

  private static String getExpressInferredType(SwiftInitializer initializer) {
    SwiftCallExpression call = PsiTreeUtil.findChildOfType(initializer, SwiftCallExpression.class);
    if (call != null) {
      return call.getInvokedExpression().getText();
    }
    return null;
  }

  private static String getLiteralInferredType(SwiftInitializer initializer) {
    SwiftLiteralExpression literal = PsiTreeUtil.findChildOfType(initializer, SwiftLiteralExpression.class);
    if (literal != null) {
      return literal.getType().getPresentableText();
    }
    return null;
  }

  @Nullable
  public static String getReturnType(SwiftFunctionDeclaration method) {
    SwiftTypeElement returnObject = PsiTreeUtil.findChildOfType(method.getFunctionResult(), SwiftTypeElement.class);
    if (returnObject != null) {
      return returnObject.getText();
    }
    return null;
  }

  @NotNull
  public static List<SwiftParameter> getParameters(SwiftFunctionDeclaration method) {
    SwiftParameterClause parameterClause = method.getParameterClause();
    if (parameterClause != null) {
      return parameterClause.getParameterList();
    }
    return emptyList();
  }

  @Nullable
  public static SwiftTypeElement getResolvedType(PsiElement element) {
    SwiftReferenceTypeElement reference = PsiTreeUtil.findChildOfType(element, SwiftReferenceTypeElement.class);
    if (reference != null) {
      PsiElement resolved = reference.resolve();
      if (resolved instanceof SwiftTypeAliasDeclaration) {
        return ((SwiftTypeAliasDeclaration) resolved).getTypeAssignment().getTypeElement();
      }
    }
    return null;
  }
}
