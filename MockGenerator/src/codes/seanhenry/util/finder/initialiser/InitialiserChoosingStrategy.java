package codes.seanhenry.util.finder.initialiser;

import com.jetbrains.swift.psi.SwiftInitializerDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;

import java.util.List;

public interface InitialiserChoosingStrategy {
  List<SwiftInitializerDeclaration> chooseInitialisers(SwiftTypeDeclaration type);
}
