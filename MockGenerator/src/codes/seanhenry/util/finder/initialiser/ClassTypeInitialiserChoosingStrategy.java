package codes.seanhenry.util.finder.initialiser;

import codes.seanhenry.util.MySwiftPsiUtil;
import com.jetbrains.swift.psi.SwiftInitializerDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;
import com.jetbrains.swift.psi.SwiftVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassTypeInitialiserChoosingStrategy implements InitialiserChoosingStrategy {

  @Override
  public List<SwiftInitializerDeclaration> chooseInitialisers(SwiftTypeDeclaration type) {
    SimplestInitialiserVisitor visitor = new SimplestInitialiserVisitor();
    type.acceptChildren(visitor);
    if (visitor.simplest == null) {
      return Collections.emptyList();
    }
    return Collections.singletonList(visitor.simplest);
  }

  private class SimplestInitialiserVisitor extends SwiftVisitor {

    private SwiftInitializerDeclaration simplest = null;

    @Override
    public void visitInitializerDeclaration(@NotNull SwiftInitializerDeclaration initializer) {
      super.visitInitializerDeclaration(initializer);
      if (MySwiftPsiUtil.isPrivate(initializer) || MySwiftPsiUtil.isFilePrivate(initializer)) {
        return;
      }
      if (simplest == null) {
        simplest = initializer;
      } else if (isSimplerThan(initializer, simplest)) {
        simplest = initializer;
      }
    }

    private boolean isSimplerThan(SwiftInitializerDeclaration lhs, SwiftInitializerDeclaration rhs) {
      boolean isSimpler = false;
      try {
        isSimpler = getParameterCount(lhs) < getParameterCount(rhs);
      } catch (NullPointerException ignored) { }
      return isSimpler;
    }

    private int getParameterCount(SwiftInitializerDeclaration initializer) throws NullPointerException {
      return initializer.getParameterClause().getParameterList().size();
    }
  }
}
