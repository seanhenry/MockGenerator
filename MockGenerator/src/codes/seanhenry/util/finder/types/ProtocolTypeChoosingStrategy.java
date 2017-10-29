package codes.seanhenry.util.finder.types;

import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.SwiftProtocolDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;

import java.util.Objects;

public class ProtocolTypeChoosingStrategy implements TypeChoosingStrategy {

  @Override
  public SwiftTypeDeclaration chooseType(PsiElement element) {
    if (element instanceof SwiftProtocolDeclaration) {
      SwiftProtocolDeclaration protocol = (SwiftProtocolDeclaration) element;
      if (!Objects.equals(protocol.getName(), "NSObjectProtocol")) {
        return protocol;
      }
    }
    return null;
  }
}
