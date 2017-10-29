package codes.seanhenry.util.finder;

import codes.seanhenry.util.ElementGatheringVisitor;
import com.jetbrains.swift.psi.SwiftFunctionDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;

import java.util.List;

public class ProtocolMethodChoosingStrategy implements MethodChoosingStrategy {

  @Override
  public List<SwiftFunctionDeclaration> chooseMethods(SwiftTypeDeclaration type) {
    ElementGatheringVisitor<SwiftFunctionDeclaration> visitor = new ElementGatheringVisitor<>(SwiftFunctionDeclaration.class);
    type.accept(visitor);
    return visitor.getElements();
  }
}
