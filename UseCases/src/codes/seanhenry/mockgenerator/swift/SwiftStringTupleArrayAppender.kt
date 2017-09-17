package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.TuplePropertyDeclaration

class SwiftStringTupleArrayAppender {
  fun transform(property: TuplePropertyDeclaration): String {
    val parameters = property.parameters
        .map { buildParameter(it) }
        .joinToString(", ")
    return property.name + ".append((" + parameters + "))"
  }

  private fun buildParameter(parameter: TuplePropertyDeclaration.TupleParameter): String {
    if (parameter.type == "Void" || parameter.type == "()") {
      return "()"
    }
    return parameter.name
  }
}
