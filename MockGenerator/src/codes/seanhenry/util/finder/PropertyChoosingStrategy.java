package codes.seanhenry.util.finder;

import com.jetbrains.swift.psi.SwiftTypeDeclaration;
import com.jetbrains.swift.psi.SwiftVariableDeclaration;

import java.util.List;

public interface PropertyChoosingStrategy {
  List<SwiftVariableDeclaration> chooseProperties(SwiftTypeDeclaration type);
}
