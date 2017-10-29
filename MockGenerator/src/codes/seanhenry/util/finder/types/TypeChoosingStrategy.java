package codes.seanhenry.util.finder.types;

import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;

public interface TypeChoosingStrategy {
  SwiftTypeDeclaration chooseType(PsiElement element);
}
