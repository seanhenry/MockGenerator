package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.AppendStringDecorator
import codes.seanhenry.mockgenerator.util.PrependStringDecorator

class CreateInvokedParameters {

  fun transform(name: String, parameters: String): PropertyDeclaration? {
    if (parameters.isEmpty()) {
      return null
    }
    val parameterList = parameters.split(",")
    val tupleParameters = parameterList
        .mapNotNull { transformParameter(it) }
    if (validateParameters(parameterList, tupleParameters)) {
      return createProperty(transformName(name), tupleParameters.filter { !isClosure(it) })
    }
    return null
  }

  private fun transformName(name: String): String {
    val invoked = PrependStringDecorator(null, "invoked")
    return AppendStringDecorator(invoked, "Parameters").process(name)
  }

  private fun validateParameters(parameterList: List<String>, tupleParameters: List<String>) =
      parameterList.size == tupleParameters.size

  private fun createProperty(name: String, tupleParameters: List<String>): PropertyDeclaration {
    val joined = tupleParameters.joinToString(", ")
    if (tupleParameters.size == 1) {
      return PropertyDeclaration(name, "($joined, Void)")
    }
    return PropertyDeclaration(name, "($joined)")
  }

  private fun isClosure(parameter: String): Boolean {
    return parameter.contains("->")
  }

  private fun transformParameter(parameter: String): String? {
    val split = parameter.split(Regex(":"), 2)
    if (split.size < 2) {
      return null
    }
    val name = getTupleParameterName(split) ?: return null
    val type = split[1]
    return name.trim() + ": " + type.trim()
  }

  private fun getTupleParameterName(parameterLabels: List<String>): String? {
    return parameterLabels[0]
        .split(" ", "\n", "\t")
        .filter { it.isNotBlank() }
        .lastOrNull()
  }
}