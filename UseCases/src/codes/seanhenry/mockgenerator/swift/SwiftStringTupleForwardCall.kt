package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.TuplePropertyDeclaration

class SwiftStringTupleForwardCall {

  fun transform(property: TuplePropertyDeclaration): String {
    return "(" + property.parameters.map { buildParameter(it) }.joinToString(", ") + ")"
  }

  private fun buildParameter(parameter: TuplePropertyDeclaration.TupleParameter): String {
    if (parameter.type == "Void" || parameter.type == "()") {
      return "()"
    }
    return parameter.name
  }
}
