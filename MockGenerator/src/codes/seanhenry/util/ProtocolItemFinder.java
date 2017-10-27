package codes.seanhenry.util;

import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.*;

import java.util.*;
import java.util.stream.Collectors;

public class ProtocolItemFinder {

  private List<SwiftProtocolDeclaration> protocols = new ArrayList<>();
  private List<SwiftVariableDeclaration> properties = new ArrayList<>();
  private List<SwiftFunctionDeclaration> methods = new ArrayList<>();
  private List<String> errors = new ArrayList<>();

  public void findItems(SwiftClassDeclaration classDeclaration) {
    List<SwiftProtocolDeclaration> protocols = getResolvedProtocols(classDeclaration);
    protocols = removeDuplicates(protocols);
    protocols = removeNSObjectProtocol(protocols);
    this.protocols = protocols;
    List<SwiftVariableDeclaration> properties = protocols
        .stream()
        .flatMap(p -> getProtocolProperties(p).stream())
        .collect(Collectors.toList());
    List<SwiftFunctionDeclaration> methods = protocols
        .stream()
        .flatMap(p -> getProtocolMethods(p).stream())
        .collect(Collectors.toList());
    this.methods = removeDuplicates(methods);
    this.properties = removeDuplicates(properties);
  }

  private static List<SwiftProtocolDeclaration> removeNSObjectProtocol(List<SwiftProtocolDeclaration> protocols) {
    return protocols.stream().filter(p -> !Objects.equals(p.getName(), "NSObjectProtocol")).collect(Collectors.toList());
  }

  private static <T> List<T> removeDuplicates(List<T> list) {
    return new ArrayList<>(new LinkedHashSet<>(list));
  }

  private static List<SwiftFunctionDeclaration> getProtocolMethods(PsiElement protocol) {
    ElementGatheringVisitor<SwiftFunctionDeclaration> visitor = new ElementGatheringVisitor<>(SwiftFunctionDeclaration.class);
    protocol.accept(visitor);
    return visitor.getElements();
  }

  private static List<SwiftVariableDeclaration> getProtocolProperties(PsiElement protocol) {
    ElementGatheringVisitor<SwiftVariableDeclaration> visitor = new ElementGatheringVisitor<>(SwiftVariableDeclaration.class);
    protocol.accept(visitor);
    return visitor.getElements();
  }

  private List<SwiftProtocolDeclaration> getResolvedProtocols(SwiftTypeDeclaration typeDeclaration) {
    SwiftTypeInheritanceClause inheritanceClause = typeDeclaration.getTypeInheritanceClause();
    if (inheritanceClause == null) {
      return Collections.emptyList();
    }
    List<SwiftProtocolDeclaration> results = inheritanceClause.getReferenceTypeElementList()
        .stream()
        .map(this::getResolvedProtocol)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    results.addAll(results
        .stream()
        .flatMap(p -> getResolvedProtocols(p).stream())
        .collect(Collectors.toList()));
    return results;
  }

  private SwiftProtocolDeclaration getResolvedProtocol(SwiftReferenceTypeElement reference) {
    PsiElement element = reference.resolve();
    if (element == null) {
      errors.add("The protocol '" + reference.getName() + "' could not be found.");
      return null;
    }
    if (element instanceof SwiftProtocolDeclaration) {
      return (SwiftProtocolDeclaration) element;
    } else if (element instanceof SwiftClassDeclaration
        && "NSObject".equals(((SwiftClassDeclaration)element).getName())) {
      // ignore NSObject
    } else {
      errors.add("This plugin currently only supports protocols.");
    }
    return null;
  }

  public List<SwiftVariableDeclaration> getProperties() {
    return properties;
  }

  public List<SwiftFunctionDeclaration> getMethods() {
    return methods;
  }

  public List<SwiftProtocolDeclaration> getProtocols() {
    return protocols;
  }

  public List<String> getErrors() {
    return errors;
  }
}
