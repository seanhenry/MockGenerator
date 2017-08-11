package codes.seanhenry.util;

import com.jetbrains.swift.psi.SwiftPsiElementFactory;
import com.jetbrains.swift.psi.SwiftTypeAnnotatedPattern;
import com.jetbrains.swift.psi.SwiftVariableDeclaration;

public class PropertyDecorator {

  public static final String OPTIONAL = "?";
  public static final String IMPLICITLY_UNWRAPPED_OPTIONAL = "!";
  private final StringDecorator stringDecorator;
  private final String optional;
  private final String scope;

  public PropertyDecorator(StringDecorator stringDecorator, String optional, String scope) {
    this.stringDecorator = stringDecorator;
    this.optional = optional;
    this.scope = scope;
  }

  public SwiftVariableDeclaration decorate(SwiftVariableDeclaration property) {
    String label = stringDecorator.process(MySwiftPsiUtil.getUnescapedPropertyName(property));
    String literal = scope + "var " + label + ": " + MySwiftPsiUtil.getResolvedTypeName(property, true) + optional;
    return (SwiftVariableDeclaration)SwiftPsiElementFactory.getInstance(property).createStatement(literal);
  }
}
