package codes.seanhenry.mockgenerator.util

class UniqueMethodNameGenerator(methodModels: List<MethodModel>) {

  private val uniqueMethodName = HashMap<String, String>()
  private val duplicateMethodModels = HashSet<MethodModel>()

  constructor(vararg methodModels: MethodModel) : this(listOf(*methodModels))

  init {
    duplicateMethodModels.addAll(methodModels)
  }

  fun generateMethodNames() {
    while (!duplicateMethodModels.isEmpty()) {
      processDuplicates()
    }
  }

  private fun processDuplicates() {
    val nameBuckets = moveDuplicatesToNameBuckets()
    nameBuckets.forEach {
      commitUniqueModels(it.key, it.value)
    }
  }

  private fun commitUniqueModels(name: String, models: MutableList<MethodModel>) {
    if (models.size == 1) {
      commitModel(name, models[0])
    } else {
      sortBySimplest(models)
      val simplestModel = models[0]
      if (isUniquelySimple(models)) {
        commitModel(name, simplestModel)
      }
      commitModelsThatCannotGetMoreComplex(models, name)
    }
  }

  private fun commitModelsThatCannotGetMoreComplex(models: List<MethodModel>, name: String) {
    models
        .filter { canModelGetMoreComplex(it) }
        .forEach { commitModel(name, it) }
  }

  private fun canModelGetMoreComplex(model: MethodModel): Boolean {
    return model.peekNextPreferredName() == null
  }

  private fun isUniquelySimple(models: List<MethodModel>): Boolean {
    val simplestParamCount = models[0].parameterCount
    val nextSimplestParamCount = models[1].parameterCount
    return simplestParamCount < nextSimplestParamCount
  }

  private fun commitModel(name: String, simplestModel: MethodModel) {
    duplicateMethodModels.remove(simplestModel)
    uniqueMethodName[simplestModel.id] = strip(name)
  }

  private fun strip(name: String): String {
    return name.replace("\\W".toRegex(), "")
  }

  // sortBy not available in Kotlin Native

  private fun sortBySimplest(models: MutableList<MethodModel>) {
    var swapped: Boolean
    do {
      swapped = false
      for (i in 1 until models.size) {
        if (models[i].parameterCount < models[i - 1].parameterCount) {
          val m = models[i - 1]
          models[i - 1] = models[i]
          models[i] = m
          swapped = true
        }
      }
    } while (swapped)
  }

  private fun moveDuplicatesToNameBuckets(): HashMap<String, MutableList<MethodModel>> {
    val nameBuckets = HashMap<String, MutableList<MethodModel>>()
    for (model in duplicateMethodModels) {
      val name = model.nextPreferredName()!!
      val models = getModels(nameBuckets, name)
      models.add(model)
      nameBuckets[name] = models
    }
    return nameBuckets
  }

  private fun getModels(nameBuckets: HashMap<String, MutableList<MethodModel>>, name: String?): MutableList<MethodModel> {
    var models: MutableList<MethodModel>? = nameBuckets[name]
    if (models == null) {
      models = ArrayList()
    }
    return models
  }

  fun getMethodName(id: String): String? {
    return uniqueMethodName[id]
  }
}

