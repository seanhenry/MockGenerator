/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package codes.seanhenry.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.swift.psi.*;
import com.jetbrains.swift.psi.impl.types.SwiftTypeUtil;
import org.jetbrains.annotations.Nullable;

public class MySwiftPsiUtil {

  public static <T extends PsiElement> T findResolvedType(PsiElement element, Class<T> type) {
    T result = findType(element, type);
    if (result != null) {
      return result;
    }
    SwiftReferenceTypeElement referenceType = findType(element, SwiftReferenceTypeElement.class);
    if (referenceType == null) {
      return null;
    }
    PsiElement resolved = referenceType.resolve();
    if (type.isInstance(resolved)) {
      return type.cast(resolved);
    } else if (resolved instanceof SwiftTypeAliasDeclaration) {
      return findTypeAliasType((SwiftTypeAliasDeclaration)resolved, type);
    }
    return null;
  }

  public static String getUnescapedPropertyName(SwiftVariableDeclaration property) {
    return getPropertyName(property).replaceAll("`", "");
  }

  private static String getPropertyName(SwiftVariableDeclaration property) {
    SwiftTypeAnnotatedPattern pattern = (SwiftTypeAnnotatedPattern) property.getPatternInitializerList().get(0).getPattern();
    return pattern.getPattern().getText();
  }

  public static SwiftTypeAnnotation getPropertyTypeAnnotation(SwiftVariableDeclaration property) {
    SwiftTypeAnnotatedPattern pattern = (SwiftTypeAnnotatedPattern) property.getPatternInitializerList().get(0).getPattern();
    return pattern.getTypeAnnotation();
  }

  @Nullable
  private static <T extends PsiElement> T findTypeAliasType(SwiftTypeAliasDeclaration typeAlias, Class<T> type) {
    SwiftTypeElement typeAliasType = typeAlias.getTypeAssignment().getTypeElement();
    if (type.isInstance(typeAliasType)) {
      return type.cast(typeAliasType);
    }
    return null;
  }

  private static <T extends PsiElement> T findType(PsiElement element, Class<T> type) {
    if (element == null) {
      return null;
    }
    if (type.isInstance(element)) {
      return type.cast(element);
    }
    return PsiTreeUtil.findChildOfType(element, type);
  }

  public static boolean isOptional(SwiftParameter parameter) {
    SwiftTypeElement type = getType(parameter);
    if (type != null) {
      return isOptional(type);
    }
    return false;
  }

  private static SwiftTypeElement getType(SwiftParameter parameter) {
    if (parameter != null && parameter.getTypeAnnotation() != null) {
      return parameter.getTypeAnnotation().getTypeElement();
    }
    return null;
  }

  public static boolean isOptional(PsiElement element) {
    return element instanceof SwiftOptionalTypeElement || element instanceof SwiftImplicitlyUnwrappedOptionalTypeElement;
  }

  public static boolean isVoid(SwiftTypeElement typeElement) {
    return SwiftTypeUtil.equalsToVoid(typeElement.getType())
           || typeElement.getText().equals("Void")
           || typeElement.getText().equals("(Void)");
  }

  public static String getResolvedTypeNameRemovingInout(SwiftParameter parameter) {
    String typeName = "";
    if (parameter.getTypeAnnotation() != null) {
      SwiftTypeElement typeElement = parameter.getTypeAnnotation().getTypeElement();
      if (typeElement instanceof SwiftInoutTypeElement) {
        SwiftInoutTypeElement inOut = (SwiftInoutTypeElement)typeElement;
        typeName = getResolvedTypeName(inOut.getTypeElement(), true);
      } else {
        typeName = getResolvedTypeName(typeElement, true);
      }
    }
    return typeName;
  }

  public static String getResolvedTypeName(PsiElement element) {
    return getResolvedTypeName(element, true);
  }

  public static String getResolvedTypeName(PsiElement element, boolean removeOptional) {
    SwiftTypeElement type = getType(element, removeOptional);
    if (type == null) {
      return null;
    }
    SwiftTypeAliasDeclaration alias = findResolvedType(type, SwiftTypeAliasDeclaration.class);
    SwiftProtocolDeclaration protocol = PsiTreeUtil.getParentOfType(alias, SwiftProtocolDeclaration.class);
    if (protocol != null) {
      return protocol.getName() + "." + type.getText();
    }
    return type.getText();
  }

  public static String getName(SwiftVariableDeclaration property) {
    SwiftPatternInitializer pattern = property.getPatternInitializerList().get(0);
    return PsiTreeUtil.findChildOfType(pattern, SwiftIdentifierPattern.class).getText();
  }

  private static SwiftTypeElement getType(PsiElement element, boolean removeOptional) {
    SwiftTypeElement type;
    if (element instanceof SwiftTypeElement) {
      type = (SwiftTypeElement)element;
    } else {
      type = PsiTreeUtil.findChildOfType(element, SwiftTypeElement.class);
    }
    if (type == null) return null;
    SwiftTypeElement nextType = PsiTreeUtil.findChildOfType(type, SwiftTypeElement.class);
    boolean isOptional = type instanceof SwiftImplicitlyUnwrappedOptionalTypeElement || type instanceof SwiftOptionalTypeElement;
    if (nextType != null && removeOptional && isOptional) {
      return nextType;
    }
    return type;
  }
}
