package codes.seanhenry.util;

import java.util.*;
import java.util.stream.Collectors;

public class UniqueMethodNameGenerator {

  private final HashMap<String, MethodModel> methodModels;
  private List<MethodModel> overloadedModels;
  private List<String> generatedComponents;
  private MethodModel generatingModel;
  private final HashMap<String, String> methodNames = new HashMap<>();

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

  public void generateMethodNames() {
    for (MethodModel m : methodModels.values()) {
      methodNames.put(m.getID(), generateMethodName(m.getID()));
    }
  }

  public String getMethodName(String id) {
    return methodNames.get(id);
  }

  private String generateMethodName(String id) {
    generatedComponents = new ArrayList<>();
    generatingModel = methodModels.get(id);
    if (generatingModel == null)
      return null;
    overloadedModels = getOverloadedMethodModels(generatingModel);
    if (overloadedModels.isEmpty())
      return joinGeneratedComponents();

    buildComponentsUsingLabels();
    filterSameParameterCountMethods();
    buildComponentsUsingTypes();
    return joinGeneratedComponents();
  }

  private List<MethodModel> getOverloadedMethodModels(MethodModel model) {
    return methodModels.values()
      .stream()
      .filter(this::equalMethodNames)
      .filter(this::excludeGeneratingModel)
      .collect(Collectors.toList());
  }

  private boolean excludeGeneratingModel(MethodModel model) {
    return !model.getID().equals(generatingModel.getID());
  }

  private boolean equalMethodNames(MethodModel model) {
    return model.getMethodName().equals(generatingModel.getMethodName());
  }

  private String joinGeneratedComponents() {
    generatedComponents.add(0, generatingModel.getMethodName());
    List<String> filtered = generatedComponents.stream()
      .filter(this::excludeUnderscore)
      .collect(Collectors.toList());
    return String.join("", filtered);
  }

  private boolean excludeUnderscore(String string) {
    return !string.equals("_");
  }

  private void buildComponentsUsingLabels() {
    for (int i = 0; i < generatingModel.getParamComponentsList().size(); i++) {
      if (!hasParameter(i))
        break;
      String param = generatingModel.getParamComponentsList().get(i).get("label");
      generatedComponents.add(toCapitalizedString(param));
      filterDifferentParameterMethods(i, MethodModel.LABEL);
    }
  }

  private void filterSameParameterCountMethods() {
    overloadedModels = overloadedModels.stream()
      .filter(m -> m.getParamComponentsList().size() == generatingModel.getParamComponentsList().size())
      .collect(Collectors.toList());
  }

  private void buildComponentsUsingTypes() {
    int insertionIndex = 0;
    for (int i = 0; i < generatingModel.getParamComponentsList().size(); i++) {
      insertionIndex++;
      if (overloadedModels.isEmpty() || !hasParameter(i))
        break;
      String type = generatingModel.getParamComponentsList().get(i).get("type");
      generatedComponents.add(i + insertionIndex, toCapitalizedString(type));
      filterDifferentParameterMethods(i, MethodModel.TYPE);
    }
  }

  private void filterDifferentParameterMethods(int paramIndex, String type) {
    overloadedModels = overloadedModels.stream()
      .filter(m -> paramIndex < m.getParamComponentsList().size())
      .filter(m -> equalParameters(paramIndex, type, m))
      .collect(Collectors.toList());
  }

  private boolean equalParameters(int paramIndex, String type, MethodModel model) {
    String item = generatingModel.getParamComponentsList().get(paramIndex).get(
      type);
    String otherItem = model.getParamComponentsList().get(paramIndex).get(type);
    return item.equals(otherItem);
  }

  private boolean hasParameter(int index) {
    return index < generatingModel.getParamComponentsList().size();
  }

  private String toCapitalizedString(String param) {
    if (param.length() > 1)
      return param.substring(0, 1).toUpperCase() + param.substring(1);
    return param.toUpperCase();
  }

  public static class MethodModel {

    public static final String LABEL = "label";
    public static final String TYPE = "type";
    private String id;
    private String methodName;
    private List<Map<String, String>> paramComponents;

    public MethodModel(String id, String methodName, String... paramLabels) {
      this.id = id;
      this.methodName = methodName;
      paramComponents = new ArrayList<>();
      for (String param : paramLabels) {
        HashMap<String, String> map = new HashMap<>();
        map.put(LABEL, getLabel(param));
        map.put(TYPE, getType(param));
        paramComponents.add(map);
      }
    }

    public String getID() {
      return id;
    }

    public List<Map<String, String>> getParamComponentsList() {
      return paramComponents;
    }

    public String getMethodName() {
      return methodName;
    }

    private String getType(String param) {
      param = param.replace(" ", "");
      String[] components = param.split(":");
      if (1 < components.length) {
        return components[1].split("=")[0];
      }
      return "";
    }

    private String getLabel(String param) {
      param = param.split(":")[0];
      return param.trim().split(" ")[0];
    }
  }
}
