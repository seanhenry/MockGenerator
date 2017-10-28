package codes.seanhenry.util;

import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.*;

import java.util.*;
import java.util.stream.Collectors;

public class SwiftTypeItemFinder {

  private List<SwiftTypeDeclaration> types = new ArrayList<>();
  private List<SwiftVariableDeclaration> properties = new ArrayList<>();
  private List<SwiftFunctionDeclaration> methods = new ArrayList<>();
  private List<String> errors = new ArrayList<>();
  private TypeStrategy strategy;

  public SwiftTypeItemFinder(TypeStrategy strategy) {
    this.strategy = strategy;
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
    this.methods = removeDuplicates(methods);
    this.properties = removeDuplicates(properties);
  }

  private static <T> List<T> removeDuplicates(List<T> list) {
    return new ArrayList<>(new LinkedHashSet<>(list));
  }

  private static List<SwiftFunctionDeclaration> getTypeMethods(PsiElement type) {
    ElementGatheringVisitor<SwiftFunctionDeclaration> visitor = new ElementGatheringVisitor<>(SwiftFunctionDeclaration.class);
    type.accept(visitor);
    return visitor.getElements();
  }

  private static List<SwiftVariableDeclaration> getTypeProperties(PsiElement type) {
    ElementGatheringVisitor<SwiftVariableDeclaration> visitor = new ElementGatheringVisitor<>(SwiftVariableDeclaration.class);
    type.accept(visitor);
    return visitor.getElements();
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
    return strategy.getType(element);
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

  // TODO:
  public List<String> getErrors() {
    return errors;
  }
}
