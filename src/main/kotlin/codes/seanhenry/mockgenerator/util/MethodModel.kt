package codes.seanhenry.mockgenerator.util

import codes.seanhenry.mockgenerator.entities.Parameter

class MethodModel(private val methodName: String, paramLabels: List<Parameter>) {

  internal class NameTypeTuple(val name: String, val type: String)

  private val namesAndTypes: List<NameTypeTuple>
  private var index = -2

  val id: String
    get() {
      var id = methodName + "("
      id += namesAndTypes.map { nameAndType -> nameAndType.name + ":" + nameAndType.type }
          .joinToString(",")
      id += ")"
      return id
    }

  val parameterCount: Int
    get() = namesAndTypes.size

  private val filteredNames: List<String>
    get() = namesAndTypes
        .map { it.name }
        .filter { isNameValid(it) }

  private val filteredTypes: List<String>
    get() = namesAndTypes
        .map { it.type }
        .filter { isTypeValid(it) }

  init {


    namesAndTypes = paramLabels.map {
      var label = it.externalName ?: it.internalName
      if (label.isEmpty()) {
        label = it.internalName
      }
      NameTypeTuple(label, removeSpecialCharacters(it.originalTypeText))
    }
  }

  private fun removeSpecialCharacters(type: String): String {
    return type.replace(Regex("\\W"), "")
  }

  constructor(methodName: String, vararg paramLabels: String) : this(methodName, ParameterUtil.getParameters(paramLabels.joinToString(", ")))

  fun nextPreferredName(): String? {
    if (!hasNextPreferredName()) {
      return null
    }
    index++
    return getPreferredNameAt(index)
  }

  private fun hasNextPreferredName(): Boolean {
    return index + 1 < filteredNames.size + filteredTypes.size
  }

  private fun getPreferredNameAt(index: Int): String {
    val name = StringBuilder(methodName)
    val names = filteredNames
    val types = filteredTypes
    val nameCount = minOf(index + 1, names.size)
    val typeCount = minOf(index + 1 - nameCount, types.size)
    var nameIndex = 0
    var typeIndex = 0
    for (nameAndType in namesAndTypes) {
      if (nameIndex < nameCount && isNameValid(nameAndType.name)) {
        nameIndex += 1
        name.append(toCapitalizedString(nameAndType.name))
      }
      if (typeIndex < typeCount && isTypeValid(nameAndType.type)) {
        typeIndex += 1
        name.append(toCapitalizedString(nameAndType.type))
      }
    }
    return name.toString()
  }

  fun peekNextPreferredName(): String? {
    if (!hasNextPreferredName()) {
      return null
    }
    return getPreferredNameAt(index + 1)
  }

  private fun isNameValid(name: String): Boolean {
    return !name.isEmpty() && name != "_"
  }

  private fun isTypeValid(type: String): Boolean {
    return !type.isEmpty()
  }

  private fun toCapitalizedString(param: String): String {
    if (param.length > 1) {
      return param.substring(0, 1).toUpperCase() + param.substring(1)
    }
    return param.toUpperCase()
  }
}
