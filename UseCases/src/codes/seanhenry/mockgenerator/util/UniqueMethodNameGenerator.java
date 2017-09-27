package codes.seanhenry.mockgenerator.util;

import java.util.*;
import java.util.stream.Stream;

public class UniqueMethodNameGenerator {

  private final HashMap<String, String> uniqueMethodName = new HashMap<>();
  private final Set<MethodModel> duplicateMethodModels = new HashSet<>();

  public UniqueMethodNameGenerator(MethodModel... methodModels) {
    this(Arrays.asList(methodModels));
  }

  public UniqueMethodNameGenerator(List<MethodModel> methodModels) {
    duplicateMethodModels.addAll(methodModels);
  }

  public void generateMethodNames() {
    while (!duplicateMethodModels.isEmpty()) {
      processDuplicates();
    }
  }

  private void processDuplicates() {
    HashMap<String, List<MethodModel>> nameBuckets = moveDuplicatesToNameBuckets();
    for (String name : new HashSet<>(nameBuckets.keySet())) {
      List<MethodModel> models = nameBuckets.get(name);
      stripUniqueModels(name, models);
    }
  }

  private void stripUniqueModels(String name, List<MethodModel> models) {
    if (models.size() == 1) {
      confirmModelIsUnique(name, models.get(0));
    } else {
      sortBySimplest(models);
      MethodModel simplestModel = models.get(0);
      if (isUniquelySimple(models)) {
        confirmModelIsUnique(name, simplestModel);
      }
      streamMaximumComplexityModels(models)
        .forEach(m -> confirmModelIsUnique(name, m));
    }
  }

  private static Stream<MethodModel> streamMaximumComplexityModels(List<MethodModel> models) {
    return models
      .stream()
      .filter(UniqueMethodNameGenerator::canModelGetMoreComplex);
  }

  private static boolean canModelGetMoreComplex(MethodModel model) {
    return model.peekNextPreferredName() == null;
  }

  private static boolean isUniquelySimple(List<MethodModel> models) {
    int simplestParamCount = models.get(0).getParameterCount();
    int nextSimplestParamCount = models.get(1).getParameterCount();
    return simplestParamCount < nextSimplestParamCount;
  }

  private void confirmModelIsUnique(String name, MethodModel simplestModel) {
    duplicateMethodModels.remove(simplestModel);
    uniqueMethodName.put(simplestModel.getID(), name);
  }

  private static void sortBySimplest(List<MethodModel> models) {
    models.sort(Comparator.comparingInt(MethodModel::getParameterCount));
  }

  private HashMap<String, List<MethodModel>> moveDuplicatesToNameBuckets() {
    HashMap<String, List<MethodModel>> nameBuckets = new HashMap<>();
    for (MethodModel model : duplicateMethodModels) {
      String name = model.nextPreferredName();
      List<MethodModel> models = getModels(nameBuckets, name);
      models.add(model);
      nameBuckets.put(name, models);
    }
    return nameBuckets;
  }

  private static List<MethodModel> getModels(HashMap<String, List<MethodModel>> nameBuckets, String name) {
    List<MethodModel> models = nameBuckets.get(name);
    if (models == null) {
      models = new ArrayList<>();
    }
    return models;
  }

  public String getMethodName(String id) {
    return uniqueMethodName.get(id);
  }
}

