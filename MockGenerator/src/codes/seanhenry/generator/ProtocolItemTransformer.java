package codes.seanhenry.generator;

import codes.seanhenry.mockgenerator.entities.Parameter;
import codes.seanhenry.mockgenerator.entities.ProtocolMethod;
import codes.seanhenry.mockgenerator.entities.ProtocolProperty;
import codes.seanhenry.mockgenerator.util.ParameterUtil;
import codes.seanhenry.mockgenerator.xcode.XcodeMockGenerator;
import codes.seanhenry.util.ProtocolItemFinder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ProtocolItemTransformer {

  private final ProtocolItemFinder protocolItemFinder;
  private XcodeMockGenerator generator;

  public ProtocolItemTransformer(ProtocolItemFinder protocolItemFinder, XcodeMockGenerator generator) {
    this.protocolItemFinder = protocolItemFinder;
    this.generator = generator;
  }

  public void transform() {
    addProtocolPropertiesToClass(protocolItemFinder.getProperties());
    addProtocolMethodsToClass(protocolItemFinder.getMethods());
  }

  private void addProtocolPropertiesToClass(List<SwiftVariableDeclaration> properties) {
    for (SwiftVariableDeclaration property : properties) {
      String name = property.getVariables().get(0).getName();
      String type = PsiTreeUtil.findChildOfType(property, SwiftTypeElement.class).getText();
      boolean hasSetter = PsiTreeUtil.findChildOfType(property, SwiftSetterClause.class) != null;
      generator.add(new ProtocolProperty(name, type, hasSetter, property.getText()));
    }
  }

  private void addProtocolMethodsToClass(List<SwiftFunctionDeclaration> methods) {
    for (SwiftFunctionDeclaration method : methods) {
      generator.add(new ProtocolMethod(
          getName(method),
          getReturnType(method),
          getParameters(method),
          method.getText()
      ));
    }
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
        return ((SwiftTypeAliasDeclaration)resolved).getTypeAssignment().getTypeElement().getText();
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
}
