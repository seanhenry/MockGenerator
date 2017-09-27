package codes.seanhenry.mockgenerator.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodModel {

  private static class NameTypeTuple {

    private final String name;
    private final String type;

    private NameTypeTuple(String name, String type) {
      this.name = name;
      this.type = type;
    }
  }

  private String methodName;
  private final List<NameTypeTuple> namesAndTypes;
  private int index = -2;

  public MethodModel(String methodName, List<String> paramLabels) {
    this.methodName = methodName;
    namesAndTypes = new ArrayList<>();
    for (String param : paramLabels) {
      namesAndTypes.add(new NameTypeTuple(getLabel(param), getType(param)));
    }
  }

  public MethodModel(String methodName, String... paramLabels) {
    this(methodName, Arrays.asList(paramLabels));
  }

  public String getID() {
    String id = getMethodName() + "(";
    id += namesAndTypes.stream().map(nameAndType -> nameAndType.name + ":" + nameAndType.type).collect(Collectors.joining(","));
    id += ")";
    return id;
  }

  int getParameterCount() {
    return namesAndTypes.size();
  }

  String nextPreferredName() {
    if (!hasNextPreferredName()) {
      return null;
    }
    index++;
    return getPreferredNameAt(index);
  }

  private boolean hasNextPreferredName() {
    return index + 1 < getFilteredNames().size() + getFilteredTypes().size();
  }

  private String getPreferredNameAt(int index) {
    StringBuilder name = new StringBuilder(getMethodName());
    List<String> names = getFilteredNames();
    List<String> types = getFilteredTypes();
    int nameCount = Math.min(index + 1, names.size());
    int typeCount = Math.min(index + 1 - nameCount, types.size());
    int nameIndex = 0;
    int typeIndex = 0;
    for (NameTypeTuple nameAndType : namesAndTypes) {
      if (nameIndex < nameCount && isNameValid(nameAndType.name)) {
        nameIndex += 1;
        name.append(toCapitalizedString(nameAndType.name));
      }
      if (typeIndex < typeCount && isTypeValid(nameAndType.type)) {
        typeIndex += 1;
        name.append(toCapitalizedString(nameAndType.type));
      }
    }
    return name.toString();
  }

  String peekNextPreferredName() {
    if (!hasNextPreferredName()) {
      return null;
    }
    return getPreferredNameAt(index + 1);
  }

  private List<String> getFilteredNames() {
    return namesAndTypes
      .stream()
      .map(nameAndType -> nameAndType.name)
      .filter(MethodModel::isNameValid)
      .collect(Collectors.toList());
  }

  private List<String> getFilteredTypes() {
    return namesAndTypes
      .stream()
      .map(nameAndType -> nameAndType.type)
      .filter(MethodModel::isTypeValid)
      .collect(Collectors.toList());
  }

  private static boolean isNameValid(String name) {
    return !name.isEmpty() && !name.equals("_");
  }

  private static boolean isTypeValid(String type) {
    return !type.isEmpty();
  }

  private static String toCapitalizedString(String param) {
    if (param.length() > 1)
      return param.substring(0, 1).toUpperCase() + param.substring(1);
    return param.toUpperCase();
  }

  private String getMethodName() {
    return methodName;
  }

  private static String getType(String param) {
    param = param.replace(" ", "");
    String[] components = param.split(":");
    if (1 < components.length) {
      return components[1].split("=")[0];
    }
    return "";
  }

  private static String getLabel(String param) {
    String labelString = param.split(":")[0];
    String[] labels = labelString.trim().split(" ");
    return labels[0];
  }
}
