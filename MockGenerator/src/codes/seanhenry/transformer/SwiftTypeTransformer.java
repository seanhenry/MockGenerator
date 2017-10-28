package codes.seanhenry.transformer;

import codes.seanhenry.mockgenerator.entities.Parameter;
import codes.seanhenry.mockgenerator.entities.ProtocolMethod;
import codes.seanhenry.mockgenerator.entities.ProtocolProperty;
import codes.seanhenry.mockgenerator.util.ParameterUtil;
import codes.seanhenry.util.SwiftTypeItemFinder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SwiftTypeTransformer {

  private final SwiftTypeItemFinder itemFinder;
  private final List<ProtocolMethod> methods;
  private final List<ProtocolProperty> properties;

  public SwiftTypeTransformer(SwiftTypeItemFinder itemFinder) {
    this.itemFinder = itemFinder;
    methods = new ArrayList<>();
    properties = new ArrayList<>();
  }

  public void transform() {
    transformProperties(itemFinder.getProperties());
    transformMethods(itemFinder.getMethods());
  }

  private void transformProperties(List<SwiftVariableDeclaration> properties) {
    for (SwiftVariableDeclaration property : properties) {
      String name = property.getVariables().get(0).getName();
      String type = PsiTreeUtil.findChildOfType(property, SwiftTypeElement.class).getText();
      boolean hasSetter = PsiTreeUtil.findChildOfType(property, SwiftSetterClause.class) != null;
      this.properties.add(new ProtocolProperty(name, type, hasSetter, property.getText()));
    }
  }

  private void transformMethods(List<SwiftFunctionDeclaration> methods) {
    for (SwiftFunctionDeclaration method : methods) {
      this.methods.add(new ProtocolMethod(
          getName(method),
          getReturnType(method),
          getParameters(method),
          getSignature(method)
      ));
    }
  }

  private String getSignature(SwiftFunctionDeclaration method) {
    SwiftCodeBlock codeBlock = method.getCodeBlock();
    if (codeBlock != null) {
      int offset = method.getStartOffsetInParent();
      int length = codeBlock.getTextOffset();
      return method.getContainingFile().getText().substring(offset, length);
    }
    return method.getText();
  }

  @NotNull
  private List<Parameter> getParameters(SwiftFunctionDeclaration method) {
    ArrayList<Parameter> parameters = new ArrayList<>();
    SwiftParameterClause parameterClause = method.getParameterClause();
    if (parameterClause != null && parameterClause.getText().length() > 1) {
      for (SwiftParameter p : parameterClause.getParameterList()) {
        parameters.add(transformParameter(p));
      }
    }
    return parameters;
  }

  @NotNull
  private Parameter transformParameter(SwiftParameter parameter) {
    Parameter p = ParameterUtil.Companion.getParameters(parameter.getText()).get(0);
    return new Parameter(
        p.getLabel(),
        p.getName(),
        p.getType(),
        getResolvedType(parameter, p.getType()),
        p.getText()
    );
  }

  private String getResolvedType(SwiftParameter parameter, String type) {
    SwiftReferenceTypeElement reference = PsiTreeUtil.findChildOfType(parameter.getTypeAnnotation(), SwiftReferenceTypeElement.class);
    if (reference != null) {
      PsiElement resolved = reference.resolve();
      if (resolved instanceof SwiftTypeAliasDeclaration) {
        return ((SwiftTypeAliasDeclaration) resolved).getTypeAssignment().getTypeElement().getText();
      }
    }
    return type;
  }

  @Nullable
  private String getReturnType(SwiftFunctionDeclaration method) {
    String returnType = null;
    SwiftTypeElement returnObject = PsiTreeUtil.findChildOfType(method.getFunctionResult(), SwiftTypeElement.class);
    if (returnObject != null) {
      returnType = returnObject.getText();
    }
    return returnType;
  }

  private String getName(SwiftFunctionDeclaration method) {
    String name = "";
    if (method.getName() != null) {
      name = method.getName();
    }
    return name;
  }

  public List<ProtocolMethod> getMethods() {
    return methods;
  }

  public List<ProtocolProperty> getProperties() {
    return properties;
  }
}
