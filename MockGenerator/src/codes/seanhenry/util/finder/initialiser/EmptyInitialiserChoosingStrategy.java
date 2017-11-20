package codes.seanhenry.util.finder.initialiser;

import com.jetbrains.swift.psi.SwiftInitializerDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;

import java.util.Collections;
import java.util.List;

public class EmptyInitialiserChoosingStrategy implements InitialiserChoosingStrategy {

  @Override
  public List<SwiftInitializerDeclaration> chooseInitialisers(SwiftTypeDeclaration type) {
    return Collections.emptyList();
  }
}
