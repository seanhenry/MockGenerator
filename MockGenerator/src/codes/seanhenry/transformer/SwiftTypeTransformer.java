package codes.seanhenry.transformer;

import codes.seanhenry.mockgenerator.entities.Parameter;
import codes.seanhenry.mockgenerator.entities.ProtocolMethod;
import codes.seanhenry.mockgenerator.entities.ProtocolProperty;
import codes.seanhenry.mockgenerator.util.ParameterUtil;
import codes.seanhenry.util.MySwiftPsiUtil;
import codes.seanhenry.util.finder.SwiftTypeItemFinder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class SwiftTypeTransformer {

  private static final String UNKNOWN_TYPE = "Any";
  private final SwiftTypeItemFinder itemFinder;
  private final List<ProtocolMethod> methods;
  private final List<ProtocolProperty> properties;
  private static final String UNKNOWN_NAME = "_";

  SwiftTypeTransformer(SwiftTypeItemFinder itemFinder) {
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
      String name = getName(property);
      if (name == null) {
        name = UNKNOWN_NAME;
      }
      String type = getType(property);
      if (type == null) {
        type = UNKNOWN_TYPE;
      }
      this.properties.add(new ProtocolProperty(name, type, isWritable(property), getSignature(property)));
    }
  }

  @Nullable
  protected abstract String getName(SwiftVariableDeclaration property);
  @Nullable
  protected abstract String getType(SwiftVariableDeclaration property);

  protected abstract boolean isWritable(SwiftVariableDeclaration property);
  @NotNull
  protected abstract String getSignature(SwiftVariableDeclaration property);

  private void transformMethods(List<SwiftFunctionDeclaration> methods) {
    for (SwiftFunctionDeclaration method : methods) {
      String name = getName(method);
      if (name == null) {
        name = UNKNOWN_NAME;
      }
      this.methods.add(new ProtocolMethod(
          name,
          getReturnType(method),
          getParameters(method),
          getSignature(method)
      ));
    }
  }

  @Nullable
  protected abstract String getName(SwiftFunctionDeclaration method);
  @Nullable
  protected abstract String getReturnType(SwiftFunctionDeclaration method);
  @NotNull
  protected abstract List<Parameter> getParameters(SwiftFunctionDeclaration method);
  @NotNull
  protected abstract String getSignature(SwiftFunctionDeclaration method);

  @NotNull
  Parameter transformParameter(SwiftParameter parameter) {
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
    SwiftTypeElement resolved = MySwiftPsiUtil.getResolvedType(parameter.getTypeAnnotation());
    if (resolved != null) {
      return resolved.getText();
    }
    return type;
  }

  public List<ProtocolMethod> getMethods() {
    return methods;
  }

  public List<ProtocolProperty> getProperties() {
    return properties;
  }
}
