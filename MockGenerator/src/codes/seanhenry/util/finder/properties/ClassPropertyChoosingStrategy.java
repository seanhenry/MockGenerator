package codes.seanhenry.util.finder.properties;

import codes.seanhenry.util.MySwiftPsiUtil;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;
import com.jetbrains.swift.psi.SwiftVariableDeclaration;
import com.jetbrains.swift.psi.SwiftVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassPropertyChoosingStrategy implements PropertyChoosingStrategy {

  @Override
  public List<SwiftVariableDeclaration> chooseProperties(SwiftTypeDeclaration type) {
    ClassPropertyVisitor visitor = new ClassPropertyVisitor();
    type.acceptChildren(visitor);
    return visitor.properties;
  }

  private class ClassPropertyVisitor extends SwiftVisitor {

    private List<SwiftVariableDeclaration> properties = new ArrayList<>();

    @Override
    public void visitVariableDeclaration(@NotNull SwiftVariableDeclaration property) {
      super.visitVariableDeclaration(property);
      if (shouldNotOverride(property)) {
        return;
      }
      properties.add(property);
    }

    private boolean shouldNotOverride(@NotNull SwiftVariableDeclaration property) {
      return property.isConstant()
          || property.isStatic()
          || MySwiftPsiUtil.isFinal(property)
          || MySwiftPsiUtil.isPrivate(property)
          || MySwiftPsiUtil.isFilePrivate(property)
          || Objects.equals(property.getContainingClass().getName(), "NSObject");
    }
  }
}
