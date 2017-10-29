package codes.seanhenry.util.finder.properties;

import com.jetbrains.swift.psi.SwiftTypeDeclaration;
import com.jetbrains.swift.psi.SwiftVariableDeclaration;

import java.util.List;

public interface PropertyChoosingStrategy {
  List<SwiftVariableDeclaration> chooseProperties(SwiftTypeDeclaration type);
}
