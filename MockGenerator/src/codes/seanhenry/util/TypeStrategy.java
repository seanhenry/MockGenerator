package codes.seanhenry.util;

import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;

public interface TypeStrategy {
  SwiftTypeDeclaration getType(PsiElement element);
}
