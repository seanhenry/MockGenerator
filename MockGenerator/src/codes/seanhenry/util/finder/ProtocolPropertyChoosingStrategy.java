package codes.seanhenry.util.finder;

import codes.seanhenry.util.ElementGatheringVisitor;
import com.jetbrains.swift.psi.SwiftFunctionDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;
import com.jetbrains.swift.psi.SwiftVariableDeclaration;

import java.util.List;

public class ProtocolPropertyChoosingStrategy implements PropertyChoosingStrategy {

  @Override
  public List<SwiftVariableDeclaration> chooseProperties(SwiftTypeDeclaration type) {
    ElementGatheringVisitor<SwiftVariableDeclaration> visitor = new ElementGatheringVisitor<>(SwiftVariableDeclaration.class);
    type.accept(visitor);
    return visitor.getElements();
  }
}
