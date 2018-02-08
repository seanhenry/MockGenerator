package codes.seanhenry.util.finder;

import codes.seanhenry.mockgenerator.entities.Initialiser;
import codes.seanhenry.util.finder.initialiser.InitialiserChoosingStrategy;
import codes.seanhenry.util.finder.methods.MethodChoosingStrategy;
import codes.seanhenry.util.finder.properties.PropertyChoosingStrategy;
import codes.seanhenry.util.finder.types.TypeChoosingStrategy;
import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.*;

import java.util.*;
import java.util.stream.Collectors;

public class SwiftTypeItemFinder implements TypeItemFinder {

  private List<SwiftTypeDeclaration> types = new ArrayList<>();
  private List<SwiftInitializerDeclaration> initialisers;
  private List<SwiftVariableDeclaration> properties = new ArrayList<>();
  private List<SwiftFunctionDeclaration> methods = new ArrayList<>();
  private List<String> errors = new ArrayList<>();
  private final TypeChoosingStrategy strategy;
  private final PropertyChoosingStrategy propertyStrategy;
  private final MethodChoosingStrategy methodStrategy;
  private final InitialiserChoosingStrategy initialiserStrategy;

  public SwiftTypeItemFinder(TypeChoosingStrategy strategy, InitialiserChoosingStrategy initialiserStrategy, PropertyChoosingStrategy propertyStrategy, MethodChoosingStrategy methodStrategy) {
    this.strategy = strategy;
    this.propertyStrategy = propertyStrategy;
    this.methodStrategy = methodStrategy;
    this.initialiserStrategy = initialiserStrategy;
  }

  public void findItems(SwiftClassDeclaration classDeclaration) {
    List<SwiftTypeDeclaration> types = getResolvedTypes(classDeclaration);
    types = removeDuplicates(types);
    this.types = types;
    List<SwiftVariableDeclaration> properties = types
        .stream()
        .flatMap(p -> getTypeProperties(p).stream())
        .collect(Collectors.toList());
    List<SwiftFunctionDeclaration> methods = types
        .stream()
        .flatMap(p -> getTypeMethods(p).stream())
        .collect(Collectors.toList());
    this.initialisers = types
        .stream()
        .flatMap(p -> getTypeInitialisers(p).stream())
        .collect(Collectors.toList());
    this.methods = removeDuplicates(methods);
    this.properties = removeDuplicates(properties);
  }

  private static <T> List<T> removeDuplicates(List<T> list) {
    return new ArrayList<>(new LinkedHashSet<>(list));
  }

  private List<SwiftFunctionDeclaration> getTypeMethods(SwiftTypeDeclaration type) {
    return methodStrategy.chooseMethods(type);
  }

  private List<SwiftVariableDeclaration> getTypeProperties(SwiftTypeDeclaration type) {
    return propertyStrategy.chooseProperties(type);
  }

  private List<SwiftInitializerDeclaration> getTypeInitialisers(SwiftTypeDeclaration type) {
    return initialiserStrategy.chooseInitialisers(type);
  }

  private List<SwiftTypeDeclaration> getResolvedTypes(SwiftTypeDeclaration typeDeclaration) {
    SwiftTypeInheritanceClause inheritanceClause = typeDeclaration.getTypeInheritanceClause();
    if (inheritanceClause == null) {
      return Collections.emptyList();
    }
    List<SwiftTypeDeclaration> results = inheritanceClause.getReferenceTypeElementList()
        .stream()
        .map(this::getResolvedType)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    results.addAll(results
        .stream()
        .flatMap(p -> getResolvedTypes(p).stream())
        .collect(Collectors.toList()));
    return results;
  }

  private SwiftTypeDeclaration getResolvedType(SwiftReferenceTypeElement reference) {
    PsiElement element = reference.resolve();
    if (element == null) {
      errors.add("The type '" + reference.getName() + "' could not be found.");
      return null;
    }
    return strategy.chooseType(element);
  }

  public List<SwiftVariableDeclaration> getProperties() {
    return properties;
  }

  public List<SwiftFunctionDeclaration> getMethods() {
    return methods;
  }

  public List<SwiftTypeDeclaration> getTypes() {
    return types;
  }

  public List<SwiftInitializerDeclaration> getInitialisers() {
    return initialisers;
  }

  // TODO:
  public List<String> getErrors() {
    return errors;
  }
}
