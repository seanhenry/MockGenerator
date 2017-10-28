package codes.seanhenry.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.jetbrains.swift.psi.*;
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

  public void convert(List<SwiftTypeDeclaration> types) {

    List<SwiftAssociatedTypeDeclaration> associatedTypes = types
        .stream()
        .flatMap(t -> getAssociatedTypes(t).stream())
        .collect(Collectors.toList());
    addGenericParametersToClass(removeDuplicates(associatedTypes));
  }

  private static List<SwiftAssociatedTypeDeclaration> getAssociatedTypes(PsiElement type) {
    ElementGatheringVisitor<SwiftAssociatedTypeDeclaration> visitor = new ElementGatheringVisitor<>(SwiftAssociatedTypeDeclaration.class);
    type.accept(visitor);
    return visitor.getElements();
  }

  private void addGenericParametersToClass(List<SwiftAssociatedTypeDeclaration> associatedTypes) {
    if (associatedTypes.isEmpty()) {
      return;
    }
    if (classDeclaration.getGenericParameterClause() != null) {
      classDeclaration.getGenericParameterClause().delete();
    }
    String literal = "<";
    literal += associatedTypes.stream().map(PsiNamedElement::getName).collect(Collectors.joining(", "));
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
