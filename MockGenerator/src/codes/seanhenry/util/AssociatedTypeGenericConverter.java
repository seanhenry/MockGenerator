package codes.seanhenry.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.jetbrains.swift.psi.*;
import com.jetbrains.swift.symbols.swiftoc.translator.ProtocolDeclaration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class AssociatedTypeGenericConverter {

  private SwiftClassDeclaration classDeclaration;

  public AssociatedTypeGenericConverter(SwiftClassDeclaration classDeclaration) {
    this.classDeclaration = classDeclaration;
  }

  public void convert() {
    List<SwiftProtocolDeclaration> protocols = getProtocols();
    List<String> associatedTypes = getAssociatedTypeNames(protocols);
    addGenericParametersToClass(removeDuplicates(associatedTypes));
  }

  private List<String> getAssociatedTypeNames(List<SwiftProtocolDeclaration> protocols) {
    AssociatedTypeVisitor visitor = new AssociatedTypeVisitor();
    protocols.stream().flatMap(p -> p.getDeclarations().stream()).forEach(s -> s.accept(visitor));
    return visitor.typeNames;
  }

  private List<SwiftProtocolDeclaration> getProtocols() {
    if (classDeclaration.getTypeInheritanceClause() == null) {
      return new ArrayList<>();
    }
    return classDeclaration.getTypeInheritanceClause().getTypeElementList().stream()
        .flatMap(t -> ProtocolResolvingVisitor.resolve(t).stream())
        .collect(Collectors.toList());
  }

  private class AssociatedTypeVisitor extends SwiftVisitor {

    private List<String> typeNames = new ArrayList<>();

    @Override
    public void visitAssociatedTypeDeclaration(@NotNull SwiftAssociatedTypeDeclaration element) {
      typeNames.add(element.getName());
    }
  }

  private static class ProtocolResolvingVisitor extends SwiftVisitor {

    private List<SwiftProtocolDeclaration> protocols = new ArrayList<>();

    private static List<SwiftProtocolDeclaration> resolve(PsiElement element) {
      ProtocolResolvingVisitor resolver = new ProtocolResolvingVisitor();
      element.accept(resolver);
      return resolver.protocols;
    }

    @Override
    public void visitReferenceTypeElement(@NotNull SwiftReferenceTypeElement element) {
      PsiElement resolved = element.resolve();
      if (resolved != null) {
        resolved.accept(this);
      }
    }

    @Override
    public void visitProtocolDeclaration(@NotNull SwiftProtocolDeclaration element) {
      protocols.add(element);
      SwiftTypeInheritanceClause clause = element.getTypeInheritanceClause();
      if (clause != null) {
        clause.getTypeElementList().forEach(t -> protocols.addAll(resolve(t)));
      }
    }
  }

  private void addGenericParametersToClass(List<String> associatedTypes) {
    if (associatedTypes.isEmpty()) {
      return;
    }
    if (classDeclaration.getGenericParameterClause() != null) {
      classDeclaration.getGenericParameterClause().delete();
    }
    String literal = "<";
    literal += associatedTypes.stream().collect(Collectors.joining(", "));
    literal += ">";
    SwiftStatement statement = getElementFactory().createStatement(literal);
    classDeclaration.addBefore(statement, classDeclaration.getTypeInheritanceClause());
  }

  @NotNull
  private SwiftPsiElementFactory getElementFactory() {
    return SwiftPsiElementFactory.getInstance(classDeclaration);
  }

  private static <T> List<T> removeDuplicates(List<T> list) {
    return new ArrayList<>(new LinkedHashSet<>(list));
  }
}
