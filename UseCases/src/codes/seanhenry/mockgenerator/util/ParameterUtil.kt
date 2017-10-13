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
      val label = getLabel(components)
      val name = getName(components)
      val type = getType(components)
      return Parameter(label, name, type, parameter)
    }

    private fun getComponents(parameters: String): List<String> {
      val pattern = Regex("([\\w][\\w0-9]*)\\s*([\\w][\\w0-9]*)?\\s*:\\s*([\\w(@].*)")
      val matches = pattern.find(parameters)
      return matches?.groupValues?.drop(1) ?: emptyList()
    }

    private fun getLabel(components: List<String>): String {
      return components.first()
    }

    private fun getName(components: List<String>): String {
      return if (components[1].isNotEmpty()) components[1] else components[0]
    }

    private fun getType(components: List<String>): String {
      return removeAnnotations(components.last()).trim()
    }

    private fun removeAnnotations(type: String): String {
      val removed = removeConventionAnnotation(type)
      return removeSimpleAnnotation(removed)
    }

    private fun removeConventionAnnotation(type: String): String {
      return type.replace(Regex("@convention\\s*\\([\\w\\s]+\\)"), "")
    }

    private fun removeSimpleAnnotation(type: String): String {
      return type.replace(Regex("@[A-z]+"), "")
    }
  }
}
