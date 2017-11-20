package codes.seanhenry.util.finder.methods;

import codes.seanhenry.util.MySwiftPsiUtil;
import com.jetbrains.swift.psi.SwiftFunctionDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;
import com.jetbrains.swift.psi.SwiftVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClassMethodChoosingStrategy implements MethodChoosingStrategy {

  @Override
  public List<SwiftFunctionDeclaration> chooseMethods(SwiftTypeDeclaration type) {
    if (Objects.equals(type.getName(), "NSObject")) {
      return Collections.emptyList();
    }
    ClassMethodVisitor visitor = new ClassMethodVisitor();
    type.acceptChildren(visitor);
    return visitor.methods;
  }

  private class ClassMethodVisitor extends SwiftVisitor {

    private List<SwiftFunctionDeclaration> methods = new ArrayList<>();

    @Override
    public void visitFunctionDeclaration(@NotNull SwiftFunctionDeclaration method) {
      super.visitFunctionDeclaration(method);
      if (shouldNotOverride(method)) {
        return;
      }
      methods.add(method);
    }

    private boolean shouldNotOverride(@NotNull SwiftFunctionDeclaration method) {
      return method.isStatic()
          || MySwiftPsiUtil.isFinal(method)
          || MySwiftPsiUtil.isPrivate(method)
          || MySwiftPsiUtil.isFilePrivate(method);
    }
  }
}
