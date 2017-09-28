package codes.seanhenry.mockgenerator.util

import java.util.Arrays

class MethodModel(private val methodName: String, paramLabels: List<String>) {

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
    namesAndTypes = paramLabels.map { NameTypeTuple(getLabel(it), getType(it)) }
  }

  constructor(methodName: String, vararg paramLabels: String) : this(methodName, Arrays.asList<String>(*paramLabels))

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
    val nameCount = Math.min(index + 1, names.size)
    val typeCount = Math.min(index + 1 - nameCount, types.size)
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

  private fun getType(param: String): String {
    val components = param.replace(" ", "")
        .split(":")
    if (components.size > 1) {
      return components[1].split("=")[0]
          .replace(Regex("\\W"), "")
    }
    return ""
  }

  private fun getLabel(param: String): String {
    val labelString = param.split(":")[0]
    val labels = labelString.trim().split(" ")
    return labels[0]
  }
}
