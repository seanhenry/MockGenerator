package codes.seanhenry.util.finder.types;

import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.SwiftClassDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;

public class ClassTypeChoosingStrategy implements TypeChoosingStrategy {

  @Override
  public SwiftTypeDeclaration chooseType(PsiElement element) {
    if (element instanceof SwiftClassDeclaration) {
      return (SwiftClassDeclaration) element;
    }
    return null;
  }
}
