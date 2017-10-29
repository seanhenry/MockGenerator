package codes.seanhenry.util.finder;

import com.jetbrains.swift.psi.SwiftFunctionDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;
import com.jetbrains.swift.psi.SwiftVariableDeclaration;
import com.jetbrains.swift.psi.SwiftVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ClassMethodChoosingStrategy implements MethodChoosingStrategy {

  @Override
  public List<SwiftFunctionDeclaration> chooseMethods(SwiftTypeDeclaration type) {
    ClassMethodVisitor visitor = new ClassMethodVisitor();
    type.acceptChildren(visitor);
    return visitor.methods;
  }

  private class ClassMethodVisitor extends SwiftVisitor {

    private List<SwiftFunctionDeclaration> methods = new ArrayList<>();

    @Override
    public void visitFunctionDeclaration(@NotNull SwiftFunctionDeclaration method) {
      super.visitFunctionDeclaration(method);
      if (method.isStatic()) {
        return;
      }
      methods.add(method);
    }
  }
}
