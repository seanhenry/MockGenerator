package codes.seanhenry.util;

import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.SwiftClassDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;

public class ClassTypeStrategy implements TypeStrategy {

  @Override
  public SwiftTypeDeclaration getType(PsiElement element) {
    if (element instanceof SwiftClassDeclaration) {
      return (SwiftClassDeclaration) element;
    }
    return null;
  }
}
