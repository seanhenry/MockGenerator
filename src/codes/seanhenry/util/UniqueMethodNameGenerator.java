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

import com.jetbrains.swift.psi.SwiftFunctionDeclaration;
import com.jetbrains.swift.psi.SwiftParameter;
import com.jetbrains.swift.psi.SwiftParameterClause;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UniqueMethodNameGenerator {

  private final HashMap<String, MethodModel> methodModels;

  public UniqueMethodNameGenerator(MethodModel... methodModels) {
    this(Arrays.asList(methodModels));
  }

  public UniqueMethodNameGenerator(List<MethodModel> methodModels) {
    HashMap<String, MethodModel> models = new HashMap<>();
    for (MethodModel m : methodModels) {
      models.put(m.getID(), m);
    }
    this.methodModels = models;
  }

  public String generate(String id) {
    MethodModel model = methodModels.get(id);
    if (model == null) return null;
    String methodName = model.getMethodName();
    List<MethodModel> overriddenModels = methodModels.values()
      .stream()
      .filter(m -> m.getMethodName().equals(methodName))
      .filter(m -> !m.getID().equals(id))
      .collect(Collectors.toList());
    if (overriddenModels.size() == 0) {
      return methodName;
    }
    String params = model.getParamLabels()
      .stream()
      .map(this::toCapitalizedString)
      .reduce("", String::concat);
    return methodName + params;
  }

  private String toCapitalizedString(String param) {
    return param.substring(0, 1).toUpperCase() + param.substring(1);
  }

  public static class MethodModel {

    private String id;
    private String methodName;
    private String[] paramLabels;

    public MethodModel(String id, String methodName, String... paramLabels) {
      this.id = id;
      this.methodName = methodName;
      this.paramLabels = paramLabels;
    }

    public String getID() {
      return id;
    }

    public List<String> getParamLabels() {
      return Arrays.asList(paramLabels);
    }

    public String getMethodName() {
      return methodName;
    }
  }
}
