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
    return findType(resolved, type);
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

  public static boolean isOptional(PsiElement element) {
    return findType(element, SwiftOptionalTypeElement.class) != null;
  }

  public static String getResolvedTypeName(PsiElement element) {
    return getResolvedTypeName(element, true);
  }

  public static String getResolvedTypeName(PsiElement element, boolean removeOptional) {
    SwiftTypeElement type =  getType(element, removeOptional);
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
    SwiftTypeAnnotatedPattern pattern = (SwiftTypeAnnotatedPattern) property.getPatternInitializerList().get(0).getPattern();
    return pattern.getPattern().getText();
  }

  private static SwiftTypeElement getType(PsiElement element, boolean removeOptional) {
    SwiftTypeElement type = PsiTreeUtil.findChildOfType(element, SwiftTypeElement.class);
    if (type == null) return null;
    SwiftTypeElement nextType = PsiTreeUtil.findChildOfType(type, SwiftTypeElement.class);
    boolean isOptional = type instanceof SwiftImplicitlyUnwrappedOptionalTypeElement || type instanceof SwiftOptionalTypeElement;
    if (nextType != null && removeOptional && isOptional) {
      return nextType;
    }
    return type;
  }
}
