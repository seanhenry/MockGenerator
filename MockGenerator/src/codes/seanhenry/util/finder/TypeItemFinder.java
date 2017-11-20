package codes.seanhenry.util.finder;

import com.jetbrains.swift.psi.SwiftFunctionDeclaration;
import com.jetbrains.swift.psi.SwiftInitializerDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;
import com.jetbrains.swift.psi.SwiftVariableDeclaration;

import java.util.List;

public interface TypeItemFinder {
  List<SwiftVariableDeclaration> getProperties();
  List<SwiftFunctionDeclaration> getMethods();
  List<SwiftTypeDeclaration> getTypes();
  SwiftInitializerDeclaration getInitialiser();
}

