package codes.seanhenry.util.finder.initialiser;

import codes.seanhenry.util.MySwiftPsiUtil;
import com.jetbrains.swift.psi.SwiftInitializerDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;
import com.jetbrains.swift.psi.SwiftVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ClassTypeInitialiserChoosingStrategy implements InitialiserChoosingStrategy {

  @Override
  public List<SwiftInitializerDeclaration> chooseInitialisers(SwiftTypeDeclaration type) {
    ClassInitialiserVisitor visitor = new ClassInitialiserVisitor();
    type.acceptChildren(visitor);
    return visitor.initialisers;
  }

  private class ClassInitialiserVisitor extends SwiftVisitor {

    private List<SwiftInitializerDeclaration> initialisers = new ArrayList<>();

    @Override
    public void visitInitializerDeclaration(@NotNull SwiftInitializerDeclaration initializer) {
      super.visitInitializerDeclaration(initializer);
      if ( MySwiftPsiUtil.isPrivate(initializer) || MySwiftPsiUtil.isFilePrivate(initializer)) {
        return;
      }
      initialisers.add(initializer);
    }
  }
}
