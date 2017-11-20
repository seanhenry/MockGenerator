package codes.seanhenry.util.finder.types;

import codes.seanhenry.util.MySwiftPsiUtil;
import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.SwiftClassDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;

import java.util.Objects;

public class ClassTypeChoosingStrategy implements TypeChoosingStrategy {

  @Override
  public SwiftTypeDeclaration chooseType(PsiElement element) {
    if (element instanceof SwiftClassDeclaration) {
      SwiftClassDeclaration classDeclaration = (SwiftClassDeclaration) element;
      if (!MySwiftPsiUtil.isFinal(classDeclaration) && !Objects.equals(classDeclaration.getName(), "NSObject")) {
        return classDeclaration;
      }
    }
    return null;
  }
}
