package codes.seanhenry.util;

import com.jetbrains.swift.psi.*;
import com.jetbrains.swift.symbols.SwiftDeclarationSpecifiers;
import com.jetbrains.swift.symbols.SwiftFailableStatus;

public class MySwiftPsiUtil {

  public static boolean isFinal(SwiftAttributesHolder attributesHolder) {
    return attributesHolder.getAttributes().hasDeclarationSpecifier(SwiftDeclarationSpecifiers.FINAL);
  }

  public static boolean isPrivate(SwiftAttributesHolder attributesHolder) {
    return attributesHolder.getAttributes().hasDeclarationSpecifier(SwiftDeclarationSpecifiers.PRIVATE);
  }

  public static boolean isFilePrivate(SwiftAttributesHolder attributesHolder) {
    return attributesHolder.getAttributes().hasDeclarationSpecifier(SwiftDeclarationSpecifiers.FILEPRIVATE);
  }

  public static boolean isPublic(SwiftAttributesHolder attributesHolder) {
    return attributesHolder.getAttributes().hasDeclarationSpecifier(SwiftDeclarationSpecifiers.PUBLIC);
  }

  public static boolean isOpen(SwiftAttributesHolder attributesHolder) {
    return attributesHolder.getAttributes().hasDeclarationSpecifier(SwiftDeclarationSpecifiers.OPEN);
  }

  public static boolean isPrivateSet(SwiftVariableDeclaration property) {
    return property.getAttributes().hasDeclarationSpecifier(SwiftDeclarationSpecifiers.PRIVATE_SET);
  }

  public static boolean isFilePrivateSet(SwiftVariableDeclaration property) {
    return property.getAttributes().hasDeclarationSpecifier(SwiftDeclarationSpecifiers.FILEPRIVATE_SET);
  }

  public static boolean isComputed(SwiftVariableDeclaration property) {
    if (property.getPatternInitializerList().isEmpty()) {
      return false;
    }
    return property.getPatternInitializerList().get(0).isComputed();
  }

  public static boolean hasExplicitType(SwiftVariableDeclaration property) {
    if (property.getPatternInitializerList().isEmpty()) {
      return false;
    }
    SwiftPattern pattern = property.getPatternInitializerList().get(0).getPattern();
    if (pattern instanceof SwiftTypeAnnotatedPattern) {
      SwiftTypeAnnotatedPattern annotation = (SwiftTypeAnnotatedPattern)pattern;
      return annotation.getTypeAnnotation().getTypeElement() != null;
    }
    return false;
  }

  public static boolean isFailable(SwiftInitializerDeclaration initializer) {
    if (initializer.getSwiftSymbol() != null) {
      return initializer.getSwiftSymbol().getFailableStatus() != SwiftFailableStatus.none;
    }
    return false;
  }
}
