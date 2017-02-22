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
    String methodName = model.getMethodName();
    List<MethodModel> models = methodModels.values()
      .stream()
      .filter(m -> m.getMethodName().equals(methodName))
      .filter(m -> !m.getID().equals(id))
      .collect(Collectors.toList());
    if (models.size() == 0) {
      return methodName;
    }
    //MethodModel otherModel = models.get(0);
    if (model.getParamLabels().length > 0) {
      String param = model.getParamLabels()[0];
      return methodName + param.substring(0, 1).toUpperCase() + param.substring(1);
    }
    return methodName;
  }

  public String generate(SwiftFunctionDeclaration function) {
    return null;//generate(function.getName(), getParameterNames(function, p -> p.getName()).toArray(new String[0]));
  }

  private List<String> getParameterNames(SwiftFunctionDeclaration function, Function<SwiftParameter, String> operation) {
    return function.getParameterClauseList().stream()
      .map(SwiftParameterClause::getParameterList)
      .flatMap(Collection::stream)
      .map(operation)
      .collect(Collectors.toList());
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

    public String[] getParamLabels() {
      return paramLabels;
    }

    public String getMethodName() {
      return methodName;
    }
  }
}
