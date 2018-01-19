package codes.seanhenry.mockgenerator.util

import codes.seanhenry.mockgenerator.entities.Parameter

class ParameterUtil {
  companion object {
    fun getParameterList(parameters: String): List<String> {
      return parameters
          .split(Regex(",(?=[\\w\\s`]+:)"))
          .map { it.trim(' ', '\n', '\t') }
          .filter { it.isNotEmpty() }
    }

    fun getParameters(parameters: String): List<Parameter> {
      return getParameterList(parameters)
          .mapNotNull { build(it) }
    }

    private fun build(parameter: String): Parameter? {
      val components = getComponents(parameter)
      if (components.isEmpty()) {
        return null
      }
      val label = components[0]
      val name = components[1]
      val type = cleanType(components[2])
      return Parameter(label, name, type, parameter)
    }

    private fun cleanType(type: String): String {
      val stripped = removeAnnotations(type)
      return removeDefaultArguments(stripped)
    }

    private fun getComponents(parameters: String): List<String> {
      val typeIndex = parameters.indexOf(":")
      if (typeIndex == -1) {
        return emptyList()
      }
      val labelName = parameters.substring(0, typeIndex)
      val type = parameters.substring(typeIndex+1) // May have more than 1 ':' if type is closure
      val components = replaceSpacesWithSpace(labelName).split(" ")
      val label = components[0]
      val name = findName(components) ?: label
      if (label.isEmpty()) {
        return emptyList()
      }
      return listOf(label, name, type)
    }

    private fun replaceSpacesWithSpace(string: String): String = string.replace(Regex("\\s+"), " ")

    private fun findName(components: List<String>): String? {
      if (components.size > 1 && components[1].isNotEmpty()) {
        return components[1]
      }
      return null
    }

    private fun removeAnnotations(type: String): String {
      val removed = removeConventionAnnotation(type)
      return removeSimpleAnnotation(removed)
    }

    private fun removeDefaultArguments(type: String): String {
      return type.split("=")[0].trim()
    }

    private fun removeConventionAnnotation(type: String): String {
      return type.replace(Regex("@convention\\s*\\([\\w\\s]+\\)"), "")
    }

    private fun removeSimpleAnnotation(type: String): String {
      return type.replace(Regex("@[A-z]+"), "")
    }
  }
}
