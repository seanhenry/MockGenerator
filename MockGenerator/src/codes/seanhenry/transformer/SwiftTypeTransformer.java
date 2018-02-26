package codes.seanhenry.transformer;

import codes.seanhenry.mockgenerator.entities.*;
import codes.seanhenry.mockgenerator.util.ParameterUtil;
import codes.seanhenry.util.MySwiftPsiUtil;
import codes.seanhenry.util.finder.TypeItemFinder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SwiftTypeTransformer {

  private static final String UNKNOWN_TYPE = "Any";
  private final TypeItemFinder itemFinder;
  private final List<Initialiser> initialisers;
  private final List<ProtocolMethod> methods;
  private final List<ProtocolProperty> properties;
  private static final String UNKNOWN_NAME = "_";

  SwiftTypeTransformer(TypeItemFinder itemFinder) {
    this.itemFinder = itemFinder;
    initialisers = new ArrayList<>();
    methods = new ArrayList<>();
    properties = new ArrayList<>();
  }

  public void transform() {
    transformInitialisers(itemFinder.getInitialisers());
    transformProperties(itemFinder.getProperties());
    transformMethods(itemFinder.getMethods());
  }

  @Nullable
  private void transformInitialisers(List<SwiftInitializerDeclaration> initialisers) {
    for (SwiftInitializerDeclaration initialiser : initialisers) {
      this.initialisers.add(
          new Initialiser(getParameters(initialiser.getParameterClause()), MySwiftPsiUtil.isFailable(initialiser), getThrows(initialiser), isProtocol())
      );
    }
  }

  private boolean getThrows(SwiftInitializerDeclaration initialiser) {
    return initialiser.isThrowing();
  }

  protected abstract boolean isProtocol();

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
      this.properties.add(new ProtocolProperty(name, type, isWritable(property), getSignature(property).trim()));
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
      String returnType = getReturnType(method);
      this.methods.add(new ProtocolMethod(
          name,
          returnType,
          getResolvedReturnType(method, returnType),
          getParameters(method.getParameterClause()),
          getSignature(method).trim(),
          getThrows(method)
      ));
    }
  }

  @Nullable
  protected abstract String getName(SwiftFunctionDeclaration method);

  @Nullable
  private String getReturnType(SwiftFunctionDeclaration method) {
    return MySwiftPsiUtil.getReturnTypeName(method);
  }

  @Nullable
  private Type getResolvedReturnType(SwiftFunctionDeclaration method, String returnType) {
    SwiftReferenceTypeElement reference = PsiTreeUtil.findChildOfType(method.getFunctionResult(), SwiftReferenceTypeElement.class);
    if (returnType == null || reference == null) {
      return null;
    }
    PsiElement resolved = reference.resolve();
    if (resolved != null && resolved.getContext() instanceof SwiftGenericParameterClause) {
      return new GenericType(returnType);
    }
    return new Type(returnType);
  }

  @NotNull
  private List<Parameter> getParameters(SwiftParameterClause parameterClause) {
    return MySwiftPsiUtil.getParameters(parameterClause)
        .stream()
        .map(this::transformParameter)
        .collect(Collectors.toList());
  }
  @NotNull
  protected abstract String getSignature(SwiftFunctionDeclaration method);

  private boolean getThrows(SwiftFunctionDeclaration method) {
    return method.isThrowing();
  }

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

  private Type getResolvedType(SwiftParameter parameter, String type) {
    SwiftReferenceTypeElement reference = PsiTreeUtil.findChildOfType(parameter.getTypeAnnotation(), SwiftReferenceTypeElement.class);
    if (reference != null) {
      PsiElement resolved = reference.resolve();
      if (resolved instanceof SwiftTypeAliasDeclaration) {
        return new Type(parameter.getSwiftType().resolveType().getPresentableText());
      } else if (resolved != null && resolved.getContext() instanceof SwiftGenericParameterClause) {
        return new GenericType(type);
      }
    }
    return new Type(type);
  }

  public List<ProtocolMethod> getMethods() {
    return methods;
  }

  public List<ProtocolProperty> getProperties() {
    return properties;
  }

  public List<Initialiser> getInitialisers() {
    return initialisers;
  }
}
